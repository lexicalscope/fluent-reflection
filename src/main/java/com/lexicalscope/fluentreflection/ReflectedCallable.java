package com.lexicalscope.fluentreflection;

import java.util.List;

public interface ReflectedCallable {
    String getName();

    ReflectedType<?> getDeclaringClass();

    int argumentCount();

    List<ReflectedType<?>> argumentTypes();
}
