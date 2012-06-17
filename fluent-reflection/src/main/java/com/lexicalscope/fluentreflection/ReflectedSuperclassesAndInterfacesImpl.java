package com.lexicalscope.fluentreflection;

import static java.util.Collections.unmodifiableList;

import java.util.List;

import com.google.inject.TypeLiteral;

final class ReflectedSuperclassesAndInterfacesImpl<T> implements ReflectedSuperclassesAndInterfaces<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private List<ReflectedClass<?>> interfacesAndSuperClass;
    private final TypeLiteral<T> typeLiteral;

    ReflectedSuperclassesAndInterfacesImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.typeLiteral = typeLiteral;
    }

    @Override public List<ReflectedClass<?>> superclassesAndInterfaces() {
        if (interfacesAndSuperClass == null) {
            interfacesAndSuperClass =
                    unmodifiableList(new TypeHierarchyCalculation(reflectedTypeFactory)
                            .interfacesAndSuperClass(typeLiteral));
        }
        return interfacesAndSuperClass;
    }
}
