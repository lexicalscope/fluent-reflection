package com.lexicalscope.fluentreflection;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
 * Copyright 2011 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

/**
 * Main entry point for the reflection library
 */
public class Reflect {
    public static <T> ReflectedClass<T> type(final Class<T> klass) {
        return new ReflectedTypeFactoryImpl().reflect(klass);
    }

    public static ReflectedMethod method(final Method method) {
        return new ReflectedTypeFactoryImpl().method(method);
    }

    public static <T> T dynamicProxy(final ProxyImplementation<T> proxyImplementation) {
        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] { proxyImplementation.proxiedInterface() },
                proxyImplementation);
    }

    public static <T> ReflectedObject<T> object(final T object) {
        return new ReflectedTypeFactoryImpl().reflect((Class<T>) object.getClass(), object);
    }
}
