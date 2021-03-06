package com.lexicalscope.fluentreflection;

import java.lang.reflect.Constructor;

import ch.lambdaj.function.convert.Converter;

import com.google.inject.TypeLiteral;

class ConvertConstructorToReflectedConstructor<T> implements Converter<Constructor<?>, FluentConstructor<T>> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final TypeLiteral<T> typeLiteral;

    public ConvertConstructorToReflectedConstructor(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.typeLiteral = typeLiteral;
    }

    public ConvertConstructorToReflectedConstructor(
            final ReflectedTypeFactory reflectedTypeFactory,
            final Class<T> klass) {
        this(reflectedTypeFactory, TypeLiteral.get(klass));
    }

    @SuppressWarnings("unchecked") @Override public FluentConstructor<T> convert(final Constructor<?> from) {
        return new FluentConstructorImpl<T>(reflectedTypeFactory, typeLiteral, (Constructor<T>) from);
    }
}
