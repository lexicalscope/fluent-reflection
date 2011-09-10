package com.lexicalscope.fluentreflection;

import java.util.List;

class ReflectedSuperclassesAndInterfacesImpl<T> implements ReflectedSuperclassesAndInterfaces<T> {
    private final Class<T> klass;
    private List<ReflectedType<?>> interfacesAndSuperClass;

    ReflectedSuperclassesAndInterfacesImpl(final Class<T> klass) {
        this.klass = klass;
    }

    @Override
    public List<ReflectedType<?>> superClassesAndInterfaces() {
        if (interfacesAndSuperClass == null) {
            interfacesAndSuperClass = new TypeHierarchyCalculation().interfacesAndSuperClass(klass);
        }
        return interfacesAndSuperClass;
    }
}
