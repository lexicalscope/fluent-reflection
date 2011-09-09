package com.lexicalscope.fluentreflection;

import java.lang.reflect.Constructor;

public interface ReflectedConstructor<T> extends ReflectedCallable {
    /**
     * Obtain the class being reflected
     * 
     * @return the class being reflected
     */
    Constructor<T> constructorUnderReflection();

    T call(Object... args);
}
