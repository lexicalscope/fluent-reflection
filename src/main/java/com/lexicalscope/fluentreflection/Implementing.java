package com.lexicalscope.fluentreflection;

import java.lang.reflect.Constructor;
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

        public MethodInvokationContext(final Method method, final Object[] args) {
            this.method = method;
            // TODO Auto-generated constructor stub
            this.args = args;
        }
    }

    private final Map<Matcher<? super ReflectedMethod>, Object> registeredMethodHandlers =
            new LinkedHashMap<Matcher<? super ReflectedMethod>, Object>();

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

        for (final Entry<Matcher<? super ReflectedMethod>, Object> registeredMethodHandler : registeredMethodHandlers
                .entrySet()) {
            if (registeredMethodHandler.getKey().matches(method)) {
                final Object registeredImplementation = registeredMethodHandler.getValue();
                final Class<?> registeredImplementationType = registeredImplementation.getClass();

                final Constructor<?>[] constructors =
                        registeredImplementationType.getDeclaredClasses()[0].getDeclaredConstructors();
                for (final Constructor<?> constructor : constructors) {
                    System.out.println(constructor);
                }

                final Constructor<?> declaredConstructor =
                        registeredImplementationType.getDeclaredClasses()[0].getDeclaredConstructor(
                                new Class[] { registeredImplementationType });
                declaredConstructor.setAccessible(true);
                return declaredConstructor
                        .newInstance(new Object[] { registeredImplementation });
            }
        }
        return new UnsupportedOperationException("no implemention found for method " + method);
    }

    @Override
    public final Class<?> proxiedInterface() {
        return typeLiteral.getRawType();
    }

    public final MethodBinding<T> matching(final Matcher<? super ReflectedMethod> methodMatcher) {
        return new MethodBinding<T>() {
            @Override
            public void execute(final Object callable) {
                registeredMethodHandlers.put(methodMatcher, callable);
            }
        };
    }

    public final String methodName() {
        return methodInvokationContext.get().method.getName();
    }
}
