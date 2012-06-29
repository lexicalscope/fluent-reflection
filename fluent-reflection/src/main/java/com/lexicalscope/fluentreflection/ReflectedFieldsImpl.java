package com.lexicalscope.fluentreflection;

import static java.util.Collections.unmodifiableList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.inject.TypeLiteral;

final class ReflectedFieldsImpl<T> implements ReflectedFields<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final ReflectedSuperclassesAndInterfaces<T> allTypes;
    private final TypeLiteral<T> typeLiteral;

    private List<FluentField> declaredFields;
    private List<FluentField> reflectedFields;

    ReflectedFieldsImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral,
            final ReflectedSuperclassesAndInterfaces<T> allTypes) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.typeLiteral = typeLiteral;
        this.allTypes = allTypes;
    }

    @Override public List<FluentField> fields() {
        if (reflectedFields == null) {
            final List<FluentField> result = new ArrayList<FluentField>();

            result.addAll(declaredFields());
            for (final FluentClass<?> klassToReflect : allTypes.superclassesAndInterfaces()) {
                result.addAll(klassToReflect.declaredFields());
            }

            Collections.reverse(result);
            reflectedFields = unmodifiableList(result);
        }
        return reflectedFields;
    }

    private List<FluentField> getDeclaredFieldsOfClass(final TypeLiteral<?> typeLiteralToReflect) {
        final List<FluentField> result = new ArrayList<FluentField>();
        final Field[] declaredFields = typeLiteralToReflect.getRawType().getDeclaredFields();
        for (final Field method : declaredFields) {
            result.add(reflectedTypeFactory.field(typeLiteralToReflect, method));
        }
        return result;
    }

    @Override public List<FluentField> declaredFields() {
        if (declaredFields == null) {
            declaredFields = unmodifiableList(getDeclaredFieldsOfClass(typeLiteral));
        }
        return declaredFields;
    }
}
