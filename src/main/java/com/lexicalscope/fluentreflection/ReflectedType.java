package com.lexicalscope.fluentreflection;

import java.util.List;

import org.hamcrest.Matcher;

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

public interface ReflectedType<T> extends ReflectedAnnotated {
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
     * All methods declared by this type
     * 
     * @return methods declared by this type
     */
    List<ReflectedMethod> declaredMethods();

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
    ReflectedMethod method(Matcher<? super ReflectedMethod> methodMatcher);

    boolean canBeBoxed(Class<?> from);

    boolean canBeUnboxed(Class<?> from);
}
