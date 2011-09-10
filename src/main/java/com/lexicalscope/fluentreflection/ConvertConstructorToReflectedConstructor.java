package com.lexicalscope.fluentreflection;

import java.lang.reflect.Constructor;

import ch.lambdaj.function.convert.Converter;

class ConvertConstructorToReflectedConstructor<T> implements Converter<Constructor<?>, ReflectedConstructor<T>> {
    private final ReflectedTypeFactory reflectedTypeFactory;

    public ConvertConstructorToReflectedConstructor(final ReflectedTypeFactory reflectedTypeFactory) {
        this.reflectedTypeFactory = reflectedTypeFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReflectedConstructor<T> convert(final Constructor<?> from) {
        return new ReflectedConstructorImpl<T>(reflectedTypeFactory, (Constructor<T>) from);
    }
}
