package com.lexicalscope.fluentreflection.bean;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static com.lexicalscope.fluentreflection.dynamicproxy.FluentProxy.dynamicProxy;

import java.lang.reflect.Proxy;
import java.util.Map;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.dynamicproxy.Implementing;
import com.lexicalscope.fluentreflection.dynamicproxy.MethodBody;

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

public class MapBean {
    public static <T> T bean(final Class<T> klass, final Map<String, Object> map) {
        return bean(type(klass), map);
    }

    public static <T> T bean(final ReflectedClass<T> klass, final Map<String, Object> map) {
        return dynamicProxy(new Implementing<T>(klass) {
            private final Object identity = new Object();
            {
                whenProxying(isHashCodeMethod())
                        .execute(new MethodBody() {
                            @Override public void body() throws Throwable {
                                returnValue(identity.hashCode());
                            }
                        });

                whenProxying(isEqualsMethod())
                        .execute(new MethodBody() {
                            @Override public void body() throws Throwable {
                                final Object that = args()[0];
                                returnValue(Proxy.isProxyClass(that.getClass())
                                        && that == proxy());
                            }
                        });

                whenProxying(isToStringMethod()).execute(new MethodBody() {
                    @Override public void body() throws Throwable {
                        returnValue(klass.name() + " " + map.toString());
                    }
                });

                whenProxying(
                        isGetter().and(
                                hasType(boolean.class).or(
                                        hasType(Boolean.class)))).execute(new MethodBody() {
                    @Override public void body() {
                        returnValue(map.containsKey(method().propertyName()));
                    }
                });

                whenProxying(isExistence()).execute(new MethodBody() {
                    @Override public void body() {
                        returnValue(map.containsKey(method().propertyName()));
                    }
                });

                whenProxying(isQuery()).execute(new MethodBody() {
                    @Override public void body() {
                        returnValue(map.get(method().propertyName()));
                    }
                });

                whenProxying(isMutator()).execute(new MethodBody() {
                    @Override public void body() {
                        map.put(method().propertyName(), args()[0]);
                    }
                });
            }
        });
    }
}
