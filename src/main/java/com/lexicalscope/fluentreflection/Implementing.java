package com.lexicalscope.fluentreflection;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.Matcher;

import com.google.inject.TypeLiteral;

public abstract class Implementing<T> implements ProxyImplementation<T> {
    private static class MethodInvokationContext {
        private final Method method;
        private final Object[] args;
        public Object result;

        public MethodInvokationContext(final Method method, final Object[] args) {
            this.method = method;
            // TODO Auto-generated constructor stub
            this.args = args;
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

    private static Type getSuperclassTypeParameter(final Class<?> subclass) {
        final Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class<?>) {
            throw new RuntimeException("Missing type parameter.");
        }
        return ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    @Override
    public final Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        methodInvokationContext.set(new MethodInvokationContext(method, args));
        try {
            for (final Entry<Matcher<? super ReflectedMethod>, MethodBody> registeredMethodHandler : registeredMethodHandlers
                    .entrySet()) {
                if (registeredMethodHandler.getKey().matches(Reflect.method(method))) {
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

    @Override
    public final Class<?> proxiedInterface() {
        return typeLiteral.getRawType();
    }

    public final MethodBinding<T> matching(final Matcher<? super ReflectedMethod> methodMatcher) {
        return new MethodBinding<T>() {
            @Override
            public void execute(final MethodBody methodBody) {
                registeredMethodHandlers.put(methodMatcher, methodBody);
            }
        };
    }

    public final String methodName() {
        return methodInvokationContext.get().method.getName();
    }

    public final void returnValue(final Object value) {
        methodInvokationContext.get().result = value;
    }
}
