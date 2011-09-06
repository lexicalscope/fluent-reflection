package com.lexicalscope.fluentreflection;

import java.lang.reflect.Constructor;

import ch.lambdaj.function.convert.Converter;

class ConvertConstructorToReflectedConstructor<T> implements Converter<Constructor<?>, ReflectedConstructor<T>> {
    @SuppressWarnings("unchecked")
    @Override
    public ReflectedConstructor<T> convert(final Constructor<?> from) {
        return new ReflectedConstructorImpl<T>((Constructor<T>) from);
    }
}
