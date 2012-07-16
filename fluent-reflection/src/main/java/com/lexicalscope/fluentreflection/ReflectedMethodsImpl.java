package com.lexicalscope.fluentreflection;

import static java.util.Collections.unmodifiableList;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.TypeLiteral;

final class ReflectedMethodsImpl<T> implements ReflectedMethods<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final ReflectedSuperclassesAndInterfaces<T> allTypes;
    private final TypeLiteral<T> typeLiteral;

    private List<FluentMethod> declaredMethods;
    private List<FluentMethod> reflectedMethods;

    ReflectedMethodsImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral,
            final ReflectedSuperclassesAndInterfaces<T> allTypes) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.typeLiteral = typeLiteral;
        this.allTypes = allTypes;
    }

    @Override public List<FluentMethod> methods() {
        if (reflectedMethods == null) {
            final List<FluentMethod> result = new ArrayList<FluentMethod>();

            for (final FluentClass<?> klassToReflect : allTypes.superclassesAndInterfaces()) {
                result.addAll(klassToReflect.declaredMethods());
            }
            result.addAll(declaredMethods());

            reflectedMethods = unmodifiableList(result);
        }
        return reflectedMethods;
    }

    @Override public List<FluentMethod> declaredMethods() {
        if (declaredMethods == null) {
            declaredMethods = unmodifiableList(getDeclaredMethodsOfClass(typeLiteral));
        }
        return declaredMethods;
    }

    private List<FluentMethod> getDeclaredMethodsOfClass(final TypeLiteral<?> typeLiteralToReflect) {
        final List<FluentMethod> result = new ArrayList<FluentMethod>();
        final Method[] declaredMethods = typeLiteralToReflect.getRawType().getDeclaredMethods();
        for (final Method method : declaredMethods) {
            result.add(reflectedTypeFactory.method(typeLiteralToReflect, method));
        }
        return result;
    }
}
