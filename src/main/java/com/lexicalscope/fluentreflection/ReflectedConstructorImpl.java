package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;

import java.lang.reflect.Constructor;
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
}
