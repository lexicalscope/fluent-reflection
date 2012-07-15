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

/**
 * Reflection access to a class or an object.
 *
 * @author tim
 *
 * @param <T> the underlying type being reflected on
 */
public interface FluentAccess<T> extends FluentAnnotated {
    /**
     * True iff the type is an interface
     *
     * @return True iff the type is an interface
     */
    boolean isInterface();

    /**
     * True iff a variable of the type represented by this class is
     * assignable from the given class?
     *
     * @param klass the class that may be assignable from
     *
     * @return true iff a variable of the type represented by this class is
     * assignable from a value with the type indicated by the parameter
     */
    boolean assignableFrom(Class<?> klass);

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

    /**
     * Is the parameter assignable from this class?
     *
     * @param klass the class that may be assignable to
     *
     * @return true iff the parameter is assignable from this class
     */
    boolean assignableTo(Class<?> klass);

    /**
     * If this class is a primitive type return the wrapped type
     *
     * @return the wrapped type or this
     */
    FluentClass<T> boxedType();

    /**
     * Call a method using a matcher, if one can be found that can also be called with the given arguments
     *
     * @param methodMatcher matches the method
     * @param args the arguments of the method
     *
     * @return the result of calling the method
     */
    FluentObject<?> call(Matcher<? super FluentMethod> methodMatcher, Object ... args);

    /**
     * Call a method by name if one can be found that can be called with the given arguments
     *
     * @param name the name of the method
     * @param args the arguments of the method
     *
     * @return the result of calling the method
     */
    FluentObject<?> call(String name, Object ... args);

    /**
     * Determines if this {@code Class} object is a primitive wrapper
     * type, and the unwrapped type can be assigned from the parameter.
     * Can the parameter be boxed into this type?
     *
     * @param from the type that maybe be assignable to this type
     *
     * @return true iff this class is a primitive wrapper type, and the
     * unwrapped type can be assigned from the parameter
     */
    boolean canBeBoxed(Class<?> from);

    /**
     * Determines if this {@code Class} object is a primitive
     * type, and the wrapped type can be assigned from the parameter.
     * Can the parameter be unboxed into this type?
     *
     * @param from the type that maybe be assignable to this type
     *
     * @return true iff this class is a primitive type, and the
     * wrapped type can be assigned from the parameter
     */
    boolean canBeUnboxed(Class<?> from);

    /**
     * Obtain the class being reflected
     *
     * @return the class being reflected
     */
    Class<T> classUnderReflection();

    /**
     * All fields declared by this type
     *
     * @return fields declared by this type
     */
    List<FluentField> declaredFields();

    /**
     * All methods declared by this type
     *
     * @return methods declared by this type
     */
    List<FluentMethod> declaredMethods();

    /**
     * Find field matching the supplied matcher
     *
     * @param fieldMatcher
     *            matches the field
     *
     * @return The first field matching the supplied matcher
     */
    FluentField field(ReflectionMatcher<FluentMember> fieldMatcher);

    /**
     * Find field by name
     *
     * @param fieldName
     *            matches the field
     *
     * @return The field with the given name
     */
    FluentField field(String fieldName);

    /**
     * All fields
     *
     * @return all the fields
     */
    List<FluentField> fields();

    /**
     * Find all fields matching the supplied matcher
     *
     * @param fieldMatcher
     *            matches the field
     *
     * @return The fields matching the supplied matcher
     */
    List<FluentField> fields(ReflectionMatcher<? super FluentField> fieldMatcher);

    /**
     * All interfaces implemented by this type
     *
     * @return all the interfaces
     */
    List<FluentClass<?>> interfaces();

    /**
     * Determines if this {@code Class} object represents a
     * primitive type.
     *
     * @return true if and only if this class represents a primitive type
     */
    boolean isPrimitive();

    /**
     * Does this type or any of its implemented types match the given matcher?
     *
     * @param typeMatcher
     *            matcher on the required type
     *
     * @return does the type or any of its supertypes match the given matcher
     */
    boolean isType(ReflectionMatcher<FluentClass<?>> typeMatcher);

    /**
     * Determines if this {@code Class} object represents the wrapper of a
     * primitive type.
     *
     * @return true iff this class is one of the primative wrapper types
     */
    boolean isUnboxable();

    /**
     * Find the first method matching the supplied matcher
     *
     * @param methodMatcher
     *            matches the method
     *
     * @return The method matching the supplied matcher
     */
    FluentMethod method(Matcher<? super FluentMethod> methodMatcher);

    /**
     * Find the first method with the given name
     *
     * @param name
     *            the name of the method
     *
     * @return The method matching the name
     */
    FluentMethod method(String name);

    /**
     * All methods
     *
     * @return all the methods
     */
    List<FluentMethod> methods();

    /**
     * Find all methods matching the supplied matcher
     *
     * @param methodMatcher
     *            matches the methods
     *
     * @return The methods matching the supplied matcher
     */
    List<FluentMethod> methods(Matcher<? super FluentMethod> methodMatcher);

    /**
     * @return the name of the class under reflection
     */
    String name();

    /**
     * @return the simple name of the class under reflection
     */
    String simpleName();

    /**
     * Return the list of all superclasses with the immediate parent first
     *
     * @return list of superclasses nearest first
     */
    List<FluentClass<?>> superclasses();

    /**
     * @return the underlying type instance
     */
    Type type();

    FluentClass<?> typeArgument(int typeParameter);

    /**
     * If this class is a primitive wrapper type return the unwrapped type
     *
     * @return the unwrapped type or this
     */
    FluentClass<T> unboxedType();
}
