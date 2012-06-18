package com.lexicalscope.fluentreflection;

import java.lang.reflect.Constructor;

public interface ReflectedConstructor<T> extends ReflectedMember {
    /**
     * Obtain the class being reflected
     * 
     * @return the class being reflected
     */
    Constructor<T> memberUnderReflection();

    T callRaw(Object... args);
}
