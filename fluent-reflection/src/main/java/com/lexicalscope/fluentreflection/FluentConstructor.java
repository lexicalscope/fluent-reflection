package com.lexicalscope.fluentreflection;

import java.lang.reflect.Constructor;

public interface FluentConstructor<T> extends FluentMember {
    /**
     * Obtain the class being reflected
     * 
     * @return the class being reflected
     */
    Constructor<T> member();

    T callRaw(Object... args);
}
