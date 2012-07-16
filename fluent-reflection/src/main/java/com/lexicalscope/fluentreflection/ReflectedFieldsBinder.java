package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.*;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.isNotStatic;

import java.util.List;

final class ReflectedFieldsBinder<T> implements ReflectedFields<T> {
    private final ReflectedFields<T> reflectedFields;
    private final Object instance;

    ReflectedFieldsBinder(
            final ReflectedFields<T> reflectedFields,
            final Object instance) {
        this.reflectedFields = reflectedFields;
        this.instance = instance;
    }

    @Override public List<FluentField> fields() {
        return convert(
                select(reflectedFields.fields(), isNotStatic()),
                new ConvertReflectedFieldToBoundReflectedField(instance));
    }

    @Override public List<FluentField> declaredFields() {
        return convert(
                select(reflectedFields.declaredFields(), isNotStatic()),
                new ConvertReflectedFieldToBoundReflectedField(instance));
    }
}
