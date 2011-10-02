package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;

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

class ConvertTypeOfObject<T> implements Converter<Object, T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final ReflectedClass<T> reflectedKlass;
    private final TypeLiteral<T> typeLiteral;
    private final Class<?> klass;

    public ConvertTypeOfObject(
            final ReflectedTypeFactory reflectedTypeFactory,
            final ReflectedClass<T> reflectedKlass,
            final TypeLiteral<T> typeLiteral) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.reflectedKlass = reflectedKlass;
        this.typeLiteral = typeLiteral;
        klass = typeLiteral.getRawType();
    }

    @Override public T convert(final Object value) {
        if (value == null) {
            return null;
        } else if (isIterable() && Iterable.class.isAssignableFrom(value.getClass())) {
            final ArrayList<Object> result = new ArrayList<Object>();

            final TypeLiteral<?> desiredCollectionType =
                    TypeLiteral.get(((ParameterizedType) typeLiteral.getSupertype(Iterable.class).getType())
                            .getActualTypeArguments()[0]);

            final ReflectedClass<?> desiredCollectionReflectedType =
                    reflectedTypeFactory.reflect(desiredCollectionType);

            final Iterable<Object> values = (Iterable<Object>) value;
            for (final Object listItem : values) {
                result.add(desiredCollectionReflectedType.convertType(listItem));
            }

            return (T) Lambda.convert(values, new ConvertTypeOfObject<Object>(
                    reflectedTypeFactory,
                    (ReflectedClass<Object>) desiredCollectionReflectedType,
                    (TypeLiteral<Object>) desiredCollectionType));
        } else if (reflectedKlass.assignableFromObject(value)) {
            return (T) value;
        } else if (reflectedKlass.canBeUnboxed(value.getClass())) {
            return (T) value;
        } else if (reflectedKlass.canBeBoxed(value.getClass())) {
            return (T) value;
        }

        final List<ReflectedMethod> valueOfMethods =
                reflectedKlass.methods(callableHasName("valueOf").and(callableHasArguments(value.getClass())).and(
                        callableHasReturnType(klass)));

        if (!valueOfMethods.isEmpty()) {
            return (T) valueOfMethods.get(0).call(value);
        }

        final List<ReflectedConstructor<T>> constructors =
                reflectedKlass.constructors(callableHasArguments(value.getClass()));

        if (!constructors.isEmpty()) {
            return constructors.get(0).call(value);
        }

        throw new ClassCastException(String.format("cannot convert %s to %s", value.getClass(), typeLiteral));
    }

    private boolean isIterable() {
        return Iterable.class.isAssignableFrom(klass);
    }
}
