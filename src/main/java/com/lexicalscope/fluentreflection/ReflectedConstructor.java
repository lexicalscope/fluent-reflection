package com.lexicalscope.fluentreflection;

import java.util.List;

public interface ReflectedConstructor<T> extends ReflectedCallable {
    List<ReflectedType<?>> getArgumentTypes();
}
