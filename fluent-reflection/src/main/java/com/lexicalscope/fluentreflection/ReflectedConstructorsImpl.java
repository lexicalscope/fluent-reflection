package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;
import static java.util.Collections.unmodifiableList;

import java.util.List;

import com.google.inject.TypeLiteral;

final class ReflectedConstructorsImpl<T> implements ReflectedConstructors<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final TypeLiteral<T> typeLiteral;

    public ReflectedConstructorsImpl(final ReflectedTypeFactory reflectedTypeFactory, final TypeLiteral<T> typeLiteral) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.typeLiteral = typeLiteral;
    }

    @Override public List<ReflectedConstructor<T>> constructors() {
        return unmodifiableList(convert(
                typeLiteral.getRawType().getConstructors(),
                new ConvertConstructorToReflectedConstructor<T>(
                        reflectedTypeFactory, typeLiteral)));
    }
}
