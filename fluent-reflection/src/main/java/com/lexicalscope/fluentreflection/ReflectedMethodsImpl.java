package com.lexicalscope.fluentreflection;

import static java.util.Collections.unmodifiableList;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.TypeLiteral;

class ReflectedMethodsImpl<T> implements ReflectedMethods<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final ReflectedSuperclassesAndInterfaces<T> allTypes;
    private final TypeLiteral<T> typeLiteral;

    private List<ReflectedMethod> declaredMethods;
    private List<ReflectedMethod> reflectedMethods;

    ReflectedMethodsImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral,
            final ReflectedSuperclassesAndInterfaces<T> allTypes) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.typeLiteral = typeLiteral;
        this.allTypes = allTypes;
    }

    @Override public List<ReflectedMethod> methods() {
        if (reflectedMethods == null) {
            final List<ReflectedMethod> result = new ArrayList<ReflectedMethod>();

            for (final ReflectedClass<?> klassToReflect : allTypes.superclassesAndInterfaces()) {
                result.addAll(klassToReflect.declaredMethods());
            }
            result.addAll(declaredMethods());

            reflectedMethods = unmodifiableList(result);
        }
        return reflectedMethods;
    }

    private List<ReflectedMethod> getDeclaredMethodsOfClass(final TypeLiteral<?> typeLiteralToReflect) {
        final List<ReflectedMethod> result = new ArrayList<ReflectedMethod>();
        final Method[] declaredMethods = typeLiteralToReflect.getRawType().getDeclaredMethods();
        for (final Method method : declaredMethods) {
            result.add(reflectedTypeFactory.method(typeLiteralToReflect, method));
        }
        return result;
    }

    @Override public List<ReflectedMethod> declaredMethods() {
        if (declaredMethods == null) {
            declaredMethods = unmodifiableList(getDeclaredMethodsOfClass(typeLiteral));
        }
        return declaredMethods;
    }
}
