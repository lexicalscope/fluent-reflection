package com.lexicalscope.fluentreflection;

import java.lang.reflect.Member;
import java.util.List;

public interface ReflectedMember extends ReflectedAnnotated {
    ReflectedClass<?> declaringClass();

    Member memberUnderReflection();

    String getName();

    String propertyName();

    int argumentCount();

    List<ReflectedClass<?>> argumentTypes();

    ReflectedClass<?> type();

    Object callRaw(Object... args);

    ReflectedObject<?> call(Object... args);

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
