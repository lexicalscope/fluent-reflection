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

public interface ReflectedType<T> {
    /**
     * Obtain the class being reflected
     * 
     * @return the class being reflected
     */
    Class<T> classUnderReflection();

    /**
     * All methods
     * 
     * @return all the methods
     */
    List<ReflectedMethod> methods();

    /**
     * Find all methods matching the supplied matcher
     * 
     * @param methodMatcher
     *            matches the methods
     * 
     * @return The methods matching the supplied matcher
     */
    List<ReflectedMethod> methods(Matcher<? super ReflectedMethod> methodMatcher);

    /**
     * Find the first method matching the supplied matcher
     * 
     * @param methodMatcher
     *            matches the method
     * 
     * @return The method matching the supplied matcher
     */
    ReflectedCallable method(Matcher<? super ReflectedMethod> methodMatcher);

    /**
     * Find the first static method matching the supplied matcher
     * 
     * @param methodNamed
     *            matches the method
     * 
     * @return The method matching the supplied matcher
     */
    ReflectedMethod staticMethod(Matcher<? super ReflectedMethod> methodNamed);

    /**
     * All interfaces implemented by this type
     * 
     * @return all the interfaces
     */
    List<ReflectedType<?>> interfaces();

    /**
     * True iff the type is an interface
     * 
     * @return True iff the type is an interface
     */
    boolean isInterface();

    /**
     * Return the list of all superclasses with the immediate parent first
     * 
     * @return list of superclasses nearest first
     */
    List<ReflectedType<?>> superclasses();

    /**
     * Construct and object of the type under reflection
     * 
     * @return the constructed object
     */
    T construct(Object... args);

    /**
     * Find all constructors matching the supplied matcher
     * 
     * @param constructorMatcher
     *            matches the constructors
     * 
     * @return
     */
    List<ReflectedConstructor<T>> constructors(ReflectionMatcher<? super ReflectedConstructor<?>> constructorMatcher);

    /**
     * Find the first constructor method matching the supplied matcher
     * 
     * @param constructorMatcher
     *            matches the method
     * 
     * @return The constructor matching the supplied matcher
     */
    ReflectedConstructor<T> constructor(ReflectionMatcher<? super ReflectedConstructor<?>> constructorMatcher);

}
