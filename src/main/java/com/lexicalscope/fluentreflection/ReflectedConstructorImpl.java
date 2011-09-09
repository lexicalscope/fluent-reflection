package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;
import static com.lexicalscope.fluentreflection.Reflect.type;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class ReflectedConstructorImpl<T> implements ReflectedConstructor<T> {
    private final Constructor<T> constructor;

    public ReflectedConstructorImpl(final Constructor<T> constructor) {
        this.constructor = constructor;
    }

    @Override
    public int argumentCount() {
        return constructor.getParameterTypes().length;
    }

    @Override
    public List<ReflectedType<?>> argumentTypes() {
        return convert(constructor.getParameterTypes(), new ConvertClassToReflectedType());
    }

    @Override
    public Constructor<T> constructorUnderReflection() {
        return constructor;
    }

    @Override
    public T call(final Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (final IllegalArgumentException e) {
            throw e;
        } catch (final InstantiationException e) {
            throw new InstantiationRuntimeException(e, constructor);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(e, constructor);
        } catch (final InvocationTargetException e) {
            throw new InvocationTargetRuntimeException(e, constructor);
        }
    }

    @Override
    public ReflectedType<?> getDeclaringClass() {
        return type(constructor.getDeclaringClass());
    }

    @Override
    public String getName() {
        return constructor.getName();
    }
}
