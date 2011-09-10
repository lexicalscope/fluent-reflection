package com.lexicalscope.fluentreflection;

import java.lang.reflect.InvocationHandler;

public interface ProxyImplementation<T> extends InvocationHandler {
    Class<?> proxiedInterface();
}
