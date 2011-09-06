package com.lexicalscope.fluentreflection;

import java.util.List;

public interface ReflectedCallable {
    int argumentCount();

    List<ReflectedType<?>> argumentTypes();
}
