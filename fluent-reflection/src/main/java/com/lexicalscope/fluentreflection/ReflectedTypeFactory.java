package com.lexicalscope.fluentreflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

interface ReflectedTypeFactory {
    <T> ReflectedClass<T> reflect(Class<T> klass);

    <T> ReflectedClass<T> reflect(TypeLiteral<T> typeLiteral);

    <T> ReflectedObject<T> reflect(Class<T> klass, T instance);

    <T> ReflectedObject<T> reflect(TypeLiteral<T> klass, T instance);

    ReflectedMethod method(Method method);

    ReflectedMethod method(TypeLiteral<?> klass, Method method);

    ReflectedField field(TypeLiteral<?> klass, Field method);
}
