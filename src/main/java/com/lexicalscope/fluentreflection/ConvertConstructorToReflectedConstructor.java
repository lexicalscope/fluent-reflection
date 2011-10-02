package com.lexicalscope.fluentreflection;

import java.lang.reflect.Constructor;

import ch.lambdaj.function.convert.Converter;

import com.google.inject.TypeLiteral;

class ConvertConstructorToReflectedConstructor<T> implements Converter<Constructor<?>, ReflectedConstructor<T>> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final TypeLiteral<T> typeLiteral;

    public ConvertConstructorToReflectedConstructor(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.typeLiteral = typeLiteral;
    }

    @SuppressWarnings("unchecked") @Override public ReflectedConstructor<T> convert(final Constructor<?> from) {
        return new ReflectedConstructorImpl<T>(reflectedTypeFactory, typeLiteral, (Constructor<T>) from);
    }
}
