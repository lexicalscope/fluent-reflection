package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import java.util.List;

import ch.lambdaj.Lambda;
import ch.lambdaj.function.convert.Converter;

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
    private final Class<?> klass;

    public ConvertTypeOfObject(
            final ReflectedTypeFactory reflectedTypeFactory,
            final ReflectedClass<T> reflectedKlass) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.reflectedKlass = reflectedKlass;
        klass = reflectedKlass.classUnderReflection();
    }

    @Override public T convert(final Object value) {
        if (value == null) {
            return null;
        } else if (isIterable() && Iterable.class.isAssignableFrom(value.getClass())) {
            final ReflectedClass<?> desiredCollectionReflectedType =
                    reflectedKlass.asType(reflectedTypeReflectingOn(Iterable.class)).typeArgument(0);

            return (T) Lambda.convert(value,
                    new ConvertTypeOfObject<Object>(
                            reflectedTypeFactory,
                            (ReflectedClass<Object>) desiredCollectionReflectedType));
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

        throw new ClassCastException(String.format("cannot convert %s to %s", value.getClass(), klass));
    }

    private boolean isIterable() {
        return Iterable.class.isAssignableFrom(klass);
    }
}
