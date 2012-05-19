package com.lexicalscope.fluentreflection;

import static java.util.Collections.unmodifiableList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.inject.TypeLiteral;

class ReflectedFieldsImpl<T> implements ReflectedFields<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final ReflectedSuperclassesAndInterfaces<T> allTypes;
    private final TypeLiteral<T> typeLiteral;

    private List<ReflectedField> declaredFields;
    private List<ReflectedField> reflectedFields;

    ReflectedFieldsImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral,
            final ReflectedSuperclassesAndInterfaces<T> allTypes) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.typeLiteral = typeLiteral;
        this.allTypes = allTypes;
    }

    @Override public List<ReflectedField> fields() {
        if (reflectedFields == null) {
            final List<ReflectedField> result = new ArrayList<ReflectedField>();

            result.addAll(declaredFields());
            for (final ReflectedClass<?> klassToReflect : allTypes.superclassesAndInterfaces()) {
                result.addAll(klassToReflect.declaredFields());
            }

            Collections.reverse(result);
            reflectedFields = unmodifiableList(result);
        }
        return reflectedFields;
    }

    private List<ReflectedField> getDeclaredFieldsOfClass(final TypeLiteral<?> typeLiteralToReflect) {
        final List<ReflectedField> result = new ArrayList<ReflectedField>();
        final Field[] declaredFields = typeLiteralToReflect.getRawType().getDeclaredFields();
        for (final Field method : declaredFields) {
            result.add(reflectedTypeFactory.field(typeLiteralToReflect, method));
        }
        return result;
    }

    @Override public List<ReflectedField> declaredFields() {
        if (declaredFields == null) {
            declaredFields = unmodifiableList(getDeclaredFieldsOfClass(typeLiteral));
        }
        return declaredFields;
    }
}
