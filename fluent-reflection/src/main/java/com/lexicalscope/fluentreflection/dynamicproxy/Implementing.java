package com.lexicalscope.fluentreflection.dynamicproxy;

import static com.lexicalscope.fluentreflection.FluentReflection.*;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static org.hamcrest.Matchers.anything;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.Matcher;

import com.google.inject.TypeLiteral;
import com.lexicalscope.fluentreflection.FluentClass;
import com.lexicalscope.fluentreflection.FluentMember;
import com.lexicalscope.fluentreflection.FluentMethod;
import com.lexicalscope.fluentreflection.FluentReflection;
import com.lexicalscope.fluentreflection.IllegalAccessRuntimeException;
import com.lexicalscope.fluentreflection.InvocationTargetRuntimeException;
import com.lexicalscope.fluentreflection.ReflectionMatcher;
import com.lexicalscope.fluentreflection.SecurityException;

public abstract class Implementing<T> implements ProxyImplementation<T> {
    private final class MethodInvoker implements MethodBody {
        private final Object subject;
        private final Method method;

        private MethodInvoker(final Object subject, final Method method) {
            this.subject = subject;
            this.method = method;
        }

        @Override public void body() throws Exception {
            try {
                method.setAccessible(true);
                returnValue(method.invoke(subject, args()));
            } catch (final SecurityException e) {
                throw new SecurityException("unable to invoke method in " + subject.getClass(), e);
            } catch (final IllegalAccessException e) {
                throw new IllegalAccessRuntimeException("unable to invoke method in "
                        + subject.getClass(), e);
            } catch (final InvocationTargetException e) {
                e.getCause();
            }
        }
    }

    private static class MethodInvokationContext {
        private final FluentMethod method;
        private final Object[] args;
        public Object result;
        private final Object proxy;

        public MethodInvokationContext(final Object proxy, final Method method, final Object[] args) {
            this.method = FluentReflection.boundMethod(proxy, method);
            this.args = args == null ? new Object[] {} : args;
            this.proxy = proxy;
        }
    }

    private final ThreadLocal<Boolean> proxyingMethod =
            new ThreadLocal<Boolean>() {
                @Override protected Boolean initialValue() {
                    return Boolean.FALSE;
                }
            };
    private final ThreadLocal<Boolean> callingDefaultHandler =
            new ThreadLocal<Boolean>() {
                @Override protected Boolean initialValue() {
                    return Boolean.FALSE;
                }
            };
    private final ThreadLocal<MethodInvokationContext> methodInvokationContext =
            new ThreadLocal<MethodInvokationContext>();

    private final Map<Matcher<? super FluentMethod>, MethodBody> registeredMethodHandlers =
            new LinkedHashMap<Matcher<? super FluentMethod>, MethodBody>();

    private final TypeLiteral<?> typeLiteral;

    public Implementing() {
        typeLiteral = TypeLiteral.get(getSuperclassTypeParameter(getClass()));
        registerDeclaredMethods();
    }

    public Implementing(final Class<?> klass) {
        typeLiteral = TypeLiteral.get(klass);
        registerDeclaredMethods();
    }

    public Implementing(final FluentClass<?> klass) {
        typeLiteral = TypeLiteral.get(klass.type());
        registerDeclaredMethods();
    }

    private static Type getSuperclassTypeParameter(final Class<?> subclass) {
        final Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class<?>) {
            throw new RuntimeException("Missing type parameter.");
        }
        return ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    private void registerDeclaredMethods() {
        for (final FluentMethod reflectedMethod : object(this).methods(
                isPublicMethod().and(isDeclaredByStrictSubtypeOf(Implementing.class)))) {
            if (reflectedMethod.argCount() == 0) {
                registeredMethodHandlers.put(
                        anything(),
                        new MethodBody() {
                            @Override public void body() throws Throwable {
                                try {
                                    reflectedMethod.call();
                                } catch (final InvocationTargetRuntimeException e) {
                                    throw e.getExceptionThrownByInvocationTarget();
                                }
                            }
                        });
            } else {
                registeredMethodHandlers.put(
                        matcherForMethodSignature(reflectedMethod),
                        new MethodInvoker(this, reflectedMethod.member()));
            }
        }
    }

    @Override public final Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        proxyingMethod.set(Boolean.TRUE);
        try
        {
            methodInvokationContext.set(new MethodInvokationContext(proxy, method, args));
            try {
                MethodBody defaultHandler = null;
                for (final Entry<Matcher<? super FluentMethod>, MethodBody> registeredMethodHandler : registeredMethodHandlers
                        .entrySet()) {
                    if (registeredMethodHandler.getKey().matches(methodInvokationContext.get().method)) {
                        final MethodBody registeredImplementation = registeredMethodHandler.getValue();
                        try {
                            registeredImplementation.body();
                        } catch (final CannotProxyThisException e) {
                            continue;
                        } catch (final CallIfUnmatchedException e) {
                            defaultHandler = registeredImplementation;
                            continue;
                        }
                        return methodInvokationContext.get().result;
                    }
                }
                if (defaultHandler != null)
                {
                    callingDefaultHandler.set(Boolean.TRUE);
                    try {
                        defaultHandler.body();
                        return methodInvokationContext.get().result;
                    } finally {
                        callingDefaultHandler.set(Boolean.FALSE);
                    }
                }
                throw new UnsupportedOperationException("no implemention found for method " + method);
            } finally {
                methodInvokationContext.set(null);
            }
        } finally {
            proxyingMethod.set(Boolean.FALSE);
        }
    }

    @Override public final Class<?> proxiedInterface() {
        return typeLiteral.getRawType();
    }

    public final MethodBinding<T> whenProxying(final Matcher<? super FluentMethod> methodMatcher) {
        if (proxyingMethod.get())
        {
            if (!methodMatcher.matches(methodInvokationContext.get().method)) {
                throw new CannotProxyThisException();
            }
            return null;
        }
        else
        {
            return new MethodBinding<T>() {
                @Override public void execute(final MethodBody methodBody) {
                    registeredMethodHandlers.put(methodMatcher, methodBody);
                }

                @Override public void execute(final QueryMethod queryMethod) {
                    execute(new MethodInvoker(queryMethod, queryMethod.getClass().getDeclaredMethods()[0]));
                }
            };
        }
    }

    public final void whenProxyingUnmatched()
    {
        if (!callingDefaultHandler.get())
        {
            throw new CallIfUnmatchedException();
        }
    }

    public final void matchingSignature(final QueryMethod queryMethod) {
        final FluentMethod userDefinedMethod = type(queryMethod.getClass()).methods().get(0);

        whenProxying(matcherForMethodSignature(userDefinedMethod)).execute(queryMethod);
    }

    private ReflectionMatcher<FluentMember> matcherForMethodSignature(final FluentMethod userDefinedMethod) {
        final List<FluentClass<?>> argumentTypes =
                new ArrayList<FluentClass<?>>(userDefinedMethod.args());

        final ReflectionMatcher<FluentMember> matchArguments =
                hasReflectedArgumentList(argumentTypes);

        final ReflectionMatcher<FluentMember> matchReturnType =
                hasType(userDefinedMethod.type());

        final ReflectionMatcher<FluentMember> matcher = matchArguments.and(matchReturnType);
        return matcher;
    }

    public final String methodName() {
        return methodInvokationContext.get().method.name();
    }

    public final FluentMethod method() {
        return methodInvokationContext.get().method;
    }

    public final void returnValue(final Object value) {
        methodInvokationContext.get().result = value;
    }

    public final Object[] args() {
        return methodInvokationContext.get().args;
    }

    public final Object proxy() {
        return methodInvokationContext.get().proxy;
    }
}
