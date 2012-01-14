package com.lexicalscope.fluentreflection;

import java.util.List;

public interface ReflectedCallable extends ReflectedAnnotated {
    String getName();

    ReflectedClass<?> declaringClass();

    int argumentCount();

    List<ReflectedClass<?>> argumentTypes();

    ReflectedClass<?> returnType();

    Object call(Object... args);

    Visibility visibility();
}