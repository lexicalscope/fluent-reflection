package com.lexicalscope.fluentreflection;

import java.lang.reflect.Constructor;

/**
 * Reflection information about a constructor.
 *
 * @author tim
 *
 * @param <T> the underlying type that the constructor instantiates
 */
public interface FluentConstructor<T> extends FluentMember {
    /**
     * Obtain the class being reflected
     *
     * @return the class being reflected
     */
    Constructor<T> member();

    FluentObject<T> call(Object... args);
}
