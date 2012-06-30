package com.lexicalscope.fluentreflection;

import static com.google.inject.internal.MoreTypes.canonicalize;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
 * Used to supply generic type information to the library. You use this class by creating anonymous subclasses of it.
 *
 * @author tim
 *
 * @param <T> the generic type information.
 */
public class TypeToken<T> {
    Type getSuperclassTypeParameter() {
        final Type superclass = this.getClass().getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        final ParameterizedType parameterized = (ParameterizedType) superclass;
        return canonicalize(parameterized.getActualTypeArguments()[0]);
    }
}
