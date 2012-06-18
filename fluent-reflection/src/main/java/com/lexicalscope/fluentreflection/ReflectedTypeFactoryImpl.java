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

final class ReflectedTypeFactoryImpl implements ReflectedTypeFactory {
    @Override public <T> FluentClass<T> reflect(final Class<T> klass) {
        return reflect(TypeLiteral.get(klass));
    }

    @Override public <T> FluentClass<T> reflect(final TypeLiteral<T> typeLiteral) {
        return new FluentClassImpl<T>(this, typeLiteral, new ReflectedMembersImpl<T>(this, typeLiteral));
    }

    @Override public <T> FluentObject<T> reflect(final Class<T> klass, final T instance) {
        return reflect(TypeLiteral.get(klass), instance);
    }

    @Override public <T> FluentObject<T> reflect(final TypeLiteral<T> klass, final T instance) {
        return new FluentObjectImpl<T>(this, reflect(klass), instance);
    }

    @Override public FluentMethod method(final Method method) {
        return method(TypeLiteral.get(method.getDeclaringClass()), method);
    }

    @Override public FluentMethod method(final TypeLiteral<?> klass, final Method method) {
        return new FluentMethodImpl(this, reflect(klass), klass, method);
    }

    public FluentMethod method(final Method method, final Object instance) {
        return new BoundFluentMethodImpl(this, method(method), instance);
    }

    @Override public ReflectedField field(final TypeLiteral<?> klass, final Field field) {
        return new FluentFieldImpl(this, reflect(klass), klass, field);
    }
}
