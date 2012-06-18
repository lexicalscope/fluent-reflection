package com.lexicalscope.fluentreflection;

import java.lang.reflect.Member;
import java.util.List;

public interface FluentMember extends FluentAnnotated {
    FluentClass<?> declarer();

    Member member();

    String name();

    String property();

    int argumentCount();

    List<FluentClass<?>> argumentTypes();

    FluentClass<?> type();

    Object callRaw(Object... args);

    FluentObject<?> call(Object... args);

    <T> Call<T> as(Class<T> returnType);

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
