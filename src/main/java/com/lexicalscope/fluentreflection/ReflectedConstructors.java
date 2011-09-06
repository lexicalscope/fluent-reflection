package com.lexicalscope.fluentreflection;

import java.util.List;

import ch.lambdaj.Lambda;

class ReflectedConstructors<T> {
    private final Class<T> klass;

    public ReflectedConstructors(final Class<T> klass) {
        this.klass = klass;
    }

    public List<ReflectedConstructor<T>> constructors() {
        return Lambda.convert(klass.getConstructors(), new ConvertConstructorToReflectedConstructor<T>());
    }
}
