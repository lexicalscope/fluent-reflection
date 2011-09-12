package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class ReflectedConstructorImpl<T> implements ReflectedConstructor<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final Constructor<T> constructor;

    public ReflectedConstructorImpl(final ReflectedTypeFactory reflectedTypeFactory, final Constructor<T> constructor) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.constructor = constructor;
    }

    @Override
    public int argumentCount() {
        return constructor.getParameterTypes().length;
    }

    @Override
    public List<ReflectedClass<?>> argumentTypes() {
        return convert(constructor.getParameterTypes(), new ConvertClassToReflectedType(reflectedTypeFactory));
    }

    @Override
    public Constructor<T> constructorUnderReflection() {
        return constructor;
    }

    @Override
    public T call(final Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (final InstantiationException e) {
            throw new InstantiationRuntimeException(e, constructor);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(e, constructor);
        } catch (final InvocationTargetException e) {
            throw new InvocationTargetRuntimeException(e, constructor);
        }
    }

    @Override
    public ReflectedClass<?> declaringClass() {
        return reflectedTypeFactory.reflect(constructor.getDeclaringClass());
    }

    @Override
    public String getName() {
        return constructor.getName();
    }

    @Override
    public ReflectedClass<?> returnType() {
        return declaringClass();
    }
}
