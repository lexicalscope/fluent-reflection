package com.lexicalscope.fluentreflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class ReflectedSuperclassesAndInterfaces<T> {
    private final Class<T> klass;

    private List<ReflectedMethod> reflectedMethods;
    private List<ReflectedType<?>> interfacesAndSuperClass;

    ReflectedSuperclassesAndInterfaces(final Class<T> klass) {
        this.klass = klass;
    }

    List<ReflectedMethod> methods() {
        if (reflectedMethods == null) {
            final List<ReflectedMethod> result = new ArrayList<ReflectedMethod>();

            result.addAll(getDeclaredMethodsOfClass(klass));
            for (final ReflectedType<?> klassToReflect : interfacesAndSuperClasses()) {
                result.addAll(getDeclaredMethodsOfClass(klassToReflect.classUnderReflection()));
            }

            reflectedMethods = result;
        }
        return reflectedMethods;
    }

    List<ReflectedType<?>> interfacesAndSuperClasses() {
        if (interfacesAndSuperClass == null) {
            interfacesAndSuperClass = new TypeHierarchyCalculation().interfacesAndSuperClass(klass);
        }
        return interfacesAndSuperClass;
    }

    private List<ReflectedMethod> getDeclaredMethodsOfClass(final Class<?> klassToReflect) {
        final List<ReflectedMethod> result = new ArrayList<ReflectedMethod>();
        final Method[] declaredMethods = klassToReflect.getDeclaredMethods();
        for (final Method method : declaredMethods) {
            result.add(new ReflectedMethodImpl(method));
        }
        return result;
    }
}
