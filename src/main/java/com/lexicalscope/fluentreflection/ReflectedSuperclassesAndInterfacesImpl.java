package com.lexicalscope.fluentreflection;

import static java.util.Collections.unmodifiableList;

import java.util.List;

class ReflectedSuperclassesAndInterfacesImpl<T> implements ReflectedSuperclassesAndInterfaces<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final Class<T> klass;
    private List<ReflectedType<?>> interfacesAndSuperClass;

    ReflectedSuperclassesAndInterfacesImpl(final ReflectedTypeFactory reflectedTypeFactory, final Class<T> klass) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.klass = klass;
    }

    @Override
    public List<ReflectedType<?>> superclassesAndInterfaces() {
        if (interfacesAndSuperClass == null) {
            interfacesAndSuperClass =
                    unmodifiableList(new TypeHierarchyCalculation(reflectedTypeFactory).interfacesAndSuperClass(klass));
        }
        return interfacesAndSuperClass;
    }
}
