package com.lexicalscope.fluentreflection;

import java.lang.reflect.Method;

import org.hamcrest.Matchers;

import com.google.inject.TypeLiteral;

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
public final class FluentReflection {
    private FluentReflection() {}

    public static <T> FluentClass<T> type(final String className) {
        try {
            return (FluentClass<T>) type(Class.forName(className));
        } catch (final ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException("unable to find class with name " + className, e);
        }
    }

    public static <T> FluentClass<T> type(final Class<T> klass) {
        return new ReflectedTypeFactoryImpl().reflect(klass);
    }

    public static <T> FluentClass<T> type(final TypeToken<T> token) {
        return (FluentClass<T>) new ReflectedTypeFactoryImpl().reflect(TypeLiteral.get(token
                .getSuperclassTypeParameter()));
    }

    public static FluentMethod method(final Method method) {
        return new ReflectedTypeFactoryImpl().method(method);
    }

    public static FluentMethod method(final Class<?> klass, final String method) {
        return type(klass).method(method);
    }

    /**
     * Find any constructor for the given class
     *
     * @param klass the type to inspect
     *
     * @return a constructor for the given class
     */
    public static <T> FluentConstructor<T> constructor(final Class<T> klass) {
        return type(klass).constructor(Matchers.any(FluentConstructor.class));
    }

    public static <T> FluentObject<T> object(final T object) {
        return new ReflectedTypeFactoryImpl().reflect((Class<T>) object.getClass(), object);
    }

    public static FluentMethod boundMethod(final Object instance, final Method method) {
        return new ReflectedTypeFactoryImpl().boundMethod(object(instance), method);
    }
}
