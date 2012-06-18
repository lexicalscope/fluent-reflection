package com.lexicalscope.fluentreflection;


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

public interface ReflectedObject<T> extends ReflectedType<T> {
    /**
     * get the static reflection information about the type of this object
     *
     * @return the static reflection information about the type of this object
     */
    ReflectedClass<T> reflectedClass();

    /**
     * The value under reflection
     *
     * @return the value that is being reflected upon
     */
    T value();

    /**
     * The value under reflection cast to a given type
     *
     * @param asType the type the value should have been cast to
     *
     * @return the value that is being reflected upon
     */
    <V> V as(Class<V> asType);
}
