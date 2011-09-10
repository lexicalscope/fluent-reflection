package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;
import static java.util.Collections.unmodifiableList;

import java.util.List;

class ReflectedConstructorsImpl<T> implements ReflectedConstructors<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final Class<T> klass;

    public ReflectedConstructorsImpl(final ReflectedTypeFactory reflectedTypeFactory, final Class<T> klass) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.klass = klass;
    }

    @Override
    public List<ReflectedConstructor<T>> constructors() {
        return unmodifiableList(convert(klass.getConstructors(), new ConvertConstructorToReflectedConstructor<T>(
                reflectedTypeFactory)));
    }
}
