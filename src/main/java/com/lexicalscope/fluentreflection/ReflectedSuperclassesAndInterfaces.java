package com.lexicalscope.fluentreflection;

import java.util.List;

interface ReflectedSuperclassesAndInterfaces<T> {
    List<ReflectedType<?>> superclassesAndInterfaces();
}
