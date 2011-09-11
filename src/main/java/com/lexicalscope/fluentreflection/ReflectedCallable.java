package com.lexicalscope.fluentreflection;

import java.util.List;

public interface ReflectedCallable {
    String getName();

    ReflectedClass<?> getDeclaringClass();

    int argumentCount();

    List<ReflectedClass<?>> argumentTypes();

    Object call(Object... args);
}
