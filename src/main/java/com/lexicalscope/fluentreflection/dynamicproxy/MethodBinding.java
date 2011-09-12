package com.lexicalscope.fluentreflection.dynamicproxy;

public interface MethodBinding<T> {
    void execute(MethodBody methodBody);

    void execute(QueryMethod queryMethod);
}
