package com.lexicalscope.fluentreflection;

import java.util.List;

public interface ReflectedCallable extends ReflectedAnnotated {
    ReflectedClass<?> declaringClass();

    String getName();

    String propertyName();

    int argumentCount();

    List<ReflectedClass<?>> argumentTypes();

    ReflectedClass<?> type();

    Object call(Object... args);

    <T> ReflectedQuery<T> castResultTo(Class<T> returnType);

    /**
     * @return true iff the method is static
     */
    boolean isStatic();

    /**
     * @return true iff the method is final
     */
    boolean isFinal();

    Visibility visibility();
}
