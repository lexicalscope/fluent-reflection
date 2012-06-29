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

import java.util.List;

import org.hamcrest.Matcher;

/**
 * Reflection information about a class.
 *
 * @author tim
 *
 * @param <T> the underlying type being reflected on
 */
public interface FluentClass<T> extends FluentAccess<T> {
    /**
     * True iff the type is an interface
     *
     * @return True iff the type is an interface
     */
    boolean isInterface();

    /**
     * Get this type as the first matching the supplied matcher. This type and
     * then the supertypes are searched
     *
     * @param typeMatcher
     *            matcher on the required type
     *
     * @return first matching the matcher
     */
    FluentClass<?> asType(Matcher<FluentClass<?>> typeMatcher);

    /**
     * Construct an object of the type under reflection
     *
     * @return the constructed object
     */
    T constructRaw(Object... args);

    /**
     * Construct an object of the type under reflection
     *
     * @return a reflection wrapper around the constructed object
     */
    FluentObject<T> construct(Object... args);

    /**
     * Find all constructors matching the supplied matcher
     *
     * @param constructorMatcher
     *            matches the constructors
     *
     * @return all constructors matching the supplied matcher
     */
    List<FluentConstructor<T>> constructors(Matcher<? super FluentConstructor<?>> constructorMatcher);

    /**
     * Find the first constructor method matching the supplied matcher
     *
     * @param constructorMatcher
     *            matches the method
     *
     * @return The constructor matching the supplied matcher
     */
    FluentConstructor<T> constructor(Matcher<? super FluentConstructor<?>> constructorMatcher);

    /**
     * Find the first static method matching the supplied matcher
     *
     * @param methodNamed
     *            matches the method
     *
     * @return The method matching the supplied matcher
     */
    FluentMethod staticMethod(Matcher<? super FluentMethod> methodNamed);
}
