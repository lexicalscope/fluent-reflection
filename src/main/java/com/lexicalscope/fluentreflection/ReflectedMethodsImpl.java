package com.lexicalscope.fluentreflection;

import static java.util.Collections.unmodifiableList;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class ReflectedMethodsImpl<T> implements ReflectedMethods<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final Class<T> klass;
    private final ReflectedSuperclassesAndInterfaces<T> allTypes;

    private List<ReflectedMethod> reflectedMethods;

    ReflectedMethodsImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final Class<T> klass,
            final ReflectedSuperclassesAndInterfaces<T> allTypes) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.klass = klass;
        this.allTypes = allTypes;
    }

    @Override
    public List<ReflectedMethod> methods() {
        if (reflectedMethods == null) {
            final List<ReflectedMethod> result = new ArrayList<ReflectedMethod>();

            result.addAll(getDeclaredMethodsOfClass(klass));
            for (final ReflectedType<?> klassToReflect : allTypes.superclassesAndInterfaces()) {
                result.addAll(getDeclaredMethodsOfClass(klassToReflect.classUnderReflection()));
            }

            reflectedMethods = unmodifiableList(result);
        }
        return reflectedMethods;
    }

    private List<ReflectedMethod> getDeclaredMethodsOfClass(final Class<?> klassToReflect) {
        final List<ReflectedMethod> result = new ArrayList<ReflectedMethod>();
        final Method[] declaredMethods = klassToReflect.getDeclaredMethods();
        for (final Method method : declaredMethods) {
            result.add(reflectedTypeFactory.method(method));
        }
        return result;
    }
}
