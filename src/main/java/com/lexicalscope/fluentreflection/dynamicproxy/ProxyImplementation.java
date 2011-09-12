package com.lexicalscope.fluentreflection.dynamicproxy;

import java.lang.reflect.InvocationHandler;

public interface ProxyImplementation<T> extends InvocationHandler {
    Class<?> proxiedInterface();
}
