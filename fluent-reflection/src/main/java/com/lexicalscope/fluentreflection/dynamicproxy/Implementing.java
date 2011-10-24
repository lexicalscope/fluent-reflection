package com.lexicalscope.fluentreflection.dynamicproxy;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

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
import com.lexicalscope.fluentreflection.FluentReflection;
import com.lexicalscope.fluentreflection.IllegalAccessRuntimeException;
import com.lexicalscope.fluentreflection.InvocationTargetRuntimeException;
import com.lexicalscope.fluentreflection.ReflectedCallable;
import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;
import com.lexicalscope.fluentreflection.SecurityException;

public abstract class Implementing<T> implements ProxyImplementation<T> {
    private static class MethodInvokationContext {
        private final ReflectedMethod method;
        private final Object[] args;
        public Object result;
        private final Object proxy;

        public MethodInvokationContext(final Object proxy, final Method method, final Object[] args) {
            this.method = FluentReflection.method(method, proxy);
            this.args = args == null ? new Object[] {} : args;
            this.proxy = proxy;
        }
    }

    private final Map<Matcher<? super ReflectedMethod>, MethodBody> registeredMethodHandlers =
            new LinkedHashMap<Matcher<? super ReflectedMethod>, MethodBody>();

    private final TypeLiteral<?> typeLiteral;
    private final ThreadLocal<MethodInvokationContext> methodInvokationContext =
            new ThreadLocal<MethodInvokationContext>();

    public Implementing() {
        typeLiteral = TypeLiteral.get(getSuperclassTypeParameter(getClass()));
    }

    public Implementing(final Class<?> klass) {
        typeLiteral = TypeLiteral.get(klass);
    }

    public Implementing(final ReflectedClass<?> klass) {
        typeLiteral = TypeLiteral.get(klass.type());
    }

    private static Type getSuperclassTypeParameter(final Class<?> subclass) {
        final Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class<?>) {
            throw new RuntimeException("Missing type parameter.");
        }
        return ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    @Override public final Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        methodInvokationContext.set(new MethodInvokationContext(proxy, method, args));
        try {
            for (final Entry<Matcher<? super ReflectedMethod>, MethodBody> registeredMethodHandler : registeredMethodHandlers
                    .entrySet()) {
                if (registeredMethodHandler.getKey().matches(methodInvokationContext.get().method)) {
                    final MethodBody registeredImplementation = registeredMethodHandler.getValue();

                    registeredImplementation.body();

                    return methodInvokationContext.get().result;
                }
            }
            throw new UnsupportedOperationException("no implemention found for method " + method);
        } finally {
            methodInvokationContext.set(null);
        }
    }

    @Override public final Class<?> proxiedInterface() {
        return typeLiteral.getRawType();
    }

    public final MethodBinding<T> matching(final Matcher<? super ReflectedMethod> methodMatcher) {
        return new MethodBinding<T>() {
            @Override public void execute(final MethodBody methodBody) {
                registeredMethodHandlers.put(methodMatcher, methodBody);
            }

            @Override public void execute(final QueryMethod queryMethod) {
                execute(new MethodBody() {
                    @Override public void body() throws Exception {
                        final Method method = queryMethod.getClass().getDeclaredMethods()[0];
                        try {
                            method.setAccessible(true);
                            returnValue(method.invoke(queryMethod, args()));
                        } catch (final SecurityException e) {
                            throw new SecurityException("unable to invoke method in " + queryMethod.getClass(), e);
                        } catch (final IllegalAccessException e) {
                            throw new IllegalAccessRuntimeException("unable to invoke method in "
                                    + queryMethod.getClass(), e);
                        } catch (final InvocationTargetException e) {
                            throw new InvocationTargetRuntimeException(e, method);
                        }
                    }
                });
            }
        };
    }

    public final void matchingSignature(final QueryMethod queryMethod) {
        final ReflectedMethod userDefinedMethod = type(queryMethod.getClass()).methods().get(0);

        final List<ReflectedClass<?>> argumentTypes =
                new ArrayList<ReflectedClass<?>>(userDefinedMethod.argumentTypes());
        //        argumentTypes.remove(0);

        final ReflectionMatcher<ReflectedCallable> matchArguments =
                callableHasReflectedArgumentList(argumentTypes);

        final ReflectionMatcher<ReflectedCallable> matchReturnType =
                callableHasReturnType(userDefinedMethod.returnType());

        matching(matchArguments.and(matchReturnType)).execute(queryMethod);
    }

    public final String methodName() {
        return methodInvokationContext.get().method.getName();
    }

    public final ReflectedMethod method() {
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
