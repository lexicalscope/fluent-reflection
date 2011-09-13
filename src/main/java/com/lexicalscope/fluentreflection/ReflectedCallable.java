package com.lexicalscope.fluentreflection;

import java.util.List;

public interface ReflectedCallable {
    String getName();

    ReflectedClass<?> declaringClass();

    int argumentCount();

    List<ReflectedClass<?>> argumentTypes();

    ReflectedClass<?> returnType();

    Object call(Object... args);

    ReflectedClass<?> annotation(ReflectionMatcher<? super ReflectedClass<?>> annotationMatcher);
}
