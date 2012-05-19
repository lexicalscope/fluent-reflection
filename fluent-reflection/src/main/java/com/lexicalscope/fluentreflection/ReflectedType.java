package com.lexicalscope.fluentreflection;

import java.lang.reflect.Type;
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
     * All interfaces implemented by this type
     *
     * @return all the interfaces
     */
    List<ReflectedClass<?>> interfaces();

    /**
     * Return the list of all superclasses with the immediate parent first
     *
     * @return list of superclasses nearest first
     */
    List<ReflectedClass<?>> superclasses();

    /**
     * Does this type or any of its implemented types match the given matcher?
     *
     * @param typeMatcher
     *            matcher on the required type
     *
     * @return does the type or any of its supertypes match the given matcher
     */
    boolean isType(ReflectionMatcher<ReflectedClass<?>> typeMatcher);

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

    /**
     * Find the first method with the given name
     *
     * @param name
     *            the name of the method
     *
     * @return The method matching the name
     */
    ReflectedMethod method(String name);

    /**
     * All fields
     *
     * @return all the fields
     */
    List<ReflectedField> fields();

    /**
     * Find all fields matching the supplied matcher
     *
     * @param fieldMatcher
     *            matches the field
     *
     * @return The fields matching the supplied matcher
     */
    List<ReflectedField> fields(ReflectionMatcher<? super ReflectedField> fieldMatcher);

    /**
     * All fields declared by this type
     *
     * @return fields declared by this type
     */
    List<ReflectedField> declaredFields();

    boolean isPrimitive();

    boolean canBeBoxed(Class<?> from);

    boolean canBeUnboxed(Class<?> from);

    ReflectedClass<T> boxedType();

    /**
     * True if the given object can be assigned to a variable of the type
     * represented by this class
     *
     * @param value
     *            the value that might be assigned
     *
     * @return true iff the value can be assigned to variables of this type
     */
    boolean assignableFromObject(Object value);

    boolean assignableTo(Class<?> klass);

    ReflectedClass<?> typeArgument(int typeParameter);

    /**
     * @return the name of the class under reflection
     */
    String name();

    /**
     * @return the simple name of the class under reflection
     */
    String simpleName();

    /**
     * @return the underlying type instance
     */
    Type type();
}
