package com.lexicalscope.fluentreflection;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class ReflectedMethodsImpl<T> implements ReflectedMethods<T> {
    private final Class<T> klass;
    private final ReflectedSuperclassesAndInterfaces<T> allTypes;

    private List<ReflectedMethod> reflectedMethods;

    ReflectedMethodsImpl(final Class<T> klass, final ReflectedSuperclassesAndInterfaces<T> allTypes) {
        this.klass = klass;
        this.allTypes = allTypes;
    }

    @Override
    public List<ReflectedMethod> methods() {
        if (reflectedMethods == null) {
            final List<ReflectedMethod> result = new ArrayList<ReflectedMethod>();

            result.addAll(getDeclaredMethodsOfClass(klass));
            for (final ReflectedType<?> klassToReflect : allTypes.superClassesAndInterfaces()) {
                result.addAll(getDeclaredMethodsOfClass(klassToReflect.classUnderReflection()));
            }

            reflectedMethods = result;
        }
        return reflectedMethods;
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
