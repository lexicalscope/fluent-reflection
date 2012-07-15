package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;
import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
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
 * A whole bunch of matchers suitable for selecting program elements
 *
 * @author tim
 */
public final class ReflectionMatchers {
    private ReflectionMatchers() { }

    public static ReflectionMatcher<FluentAnnotated> annotatedWith(final Class<? extends Annotation> annotation) {
        return new MatcherCallableAnnotatedWith(annotation);
    }

    public static ReflectionMatcher<FluentAccess<?>> anyFluentType() {
        return new MatcherAnyFluentType();
    }

    public static ReflectionMatcher<FluentAccess<?>> assignableFrom(final Class<?> klass) {
        return new MatcherAssignableFrom(klass);
    }

    public static ReflectionMatcher<FluentAccess<?>> assignableFrom(final FluentAccess<?> klass) {
        return new MatcherAssignableFrom(klass);
    }

    public static ReflectionMatcher<FluentMember> canBeCalledWithArguments(final Object ... args) {
        final List<Matcher<? super FluentClass<?>>> types = new ArrayList<Matcher<? super FluentClass<?>>>();
        for (final Object object : args) {
            if(object == null) {
                types.add(anyFluentType());
            } else {
                final FluentClass<? extends Object> argumentType = type(object.getClass());
                if(argumentType.isPrimitive()) {
                    types.add(assignableFrom(argumentType).or(assignableFrom(argumentType.boxedType())));
                } else if(argumentType.isUnboxable()) {
                    types.add(assignableFrom(argumentType).or(assignableFrom(argumentType.unboxedType())));
                } else {
                    types.add(assignableFrom(argumentType));
                }
            }
        }
        return hasArgumentListMatching(types);
    }

    /**
     * Matches the declaring class of a member
     *
     * @param declaringClass
     *            the declaring class
     *
     * @return true iff the member is declared by the argument
     */
    public static ReflectionMatcher<FluentMember> declaredBy(final Class<?> declaringClass) {
        return new MatcherDeclaredBy(reflectingOn(declaringClass));
    }

    public static ReflectionMatcher<FluentMember> hasArgumentCount(final int argumentCount) {
        return new MatcherArgumentCount(argumentCount);
    }

    public static ReflectionMatcher<FluentMember> hasArgumentList(final List<Class<?>> argTypes) {
        return new MatcherArgumentTypes(convert(argTypes, new ConvertClassToReflectedTypeAssignableMatcher()));
    }

    public static ReflectionMatcher<FluentMember> hasArgumentListMatching(
            final List<Matcher<? super FluentClass<?>>> argTypes) {
        return new MatcherArgumentTypes(argTypes);
    }

    public static ReflectionMatcher<FluentMember> hasArguments(final Class<?>... argTypes) {
        return hasArgumentList(asList(argTypes));
    }

    public static ReflectionMatcher<FluentMember> hasArgumentsMatching(
            final Matcher<? super FluentClass<?>>... argTypes) {
        return hasArgumentListMatching(asList(argTypes));
    }

    public static ReflectionMatcher<FluentClass<?>> hasInterface(final Class<?> interfac3) {
        return new MatcherHasInterface(interfac3);
    }

    /**
     * Matches the name of a callable
     *
     * @param name
     *            the name
     *
     * @return true iff the argument is equal to the name of the callable
     */
    public static ReflectionMatcher<FluentMember> hasName(final String name) {
        return new MatcherNamed(name);
    }

    /**
     * Matches a substring of the name of a callable
     *
     * @param substring
     *            the substring
     *
     * @return true iff the argument is contained within the name of the
     *         callable
     */
    public static ReflectionMatcher<FluentMember> hasNameContaining(final CharSequence substring) {
        return new MatcherHasNameContaining(substring);
    }

    /**
     * Matches a suffix of the name of a callable
     *
     * @param suffix
     *            the suffix
     *
     * @return true iff the argument is a suffix of the name of the callable
     */
    public static ReflectionMatcher<FluentMember> hasNameEndingWith(final String suffix) {
        return new MatcherHasNameEndingWith(suffix);
    }

    /**
     * Matches a regular expression against the name of a callable
     *
     * @param regex
     *            the regular expression
     *
     * @return true iff the argument is a regular expression matching the name
     *         of the callable
     */
    public static ReflectionMatcher<FluentMember> hasNameMatching(final String regex) {
        return new MatcherHasNameMatching(regex);
    }

    /**
     * Matches a prefix of the name of a callable
     *
     * @param prefix
     *            the prefix
     *
     * @return true iff the argument is a prefix of the name of the callable
     */
    public static ReflectionMatcher<FluentMember> hasNameStartingWith(final String prefix) {
        return new MatcherHasNameStartingWith(prefix);
    }

    public static ReflectionMatcher<FluentMember> hasNoArguments() {
        return hasArguments();
    }

    public static ReflectionMatcher<FluentClass<?>> hasNoInterfaces() {
        return new MatcherHasNoInterfaces();
    }

    public static ReflectionMatcher<FluentMember> hasNonVoidType() {
        return not(hasVoidType());
    }

    public static ReflectionMatcher<FluentClass<?>> hasNoSuperclasses() {
        return new MatcherHasNoSuperclasses();
    }

    public static ReflectionMatcher<FluentMember> hasPropertyName(final String name) {
        return new MatcherPropertyName(name);
    }

    public static ReflectionMatcher<FluentMember> hasReflectedArgumentList(
            final List<FluentClass<?>> argTypes) {
        return new MatcherArgumentTypes(convert(argTypes, new ConvertFluentTypeToFluentTypeAssignableMatcher()));
    }

    public static ReflectionMatcher<FluentMember> hasReflectedArguments(
            final FluentClass<?>... argTypes) {
        return hasReflectedArgumentList(asList(argTypes));
    }

    public static ReflectionMatcher<FluentClass<?>> hasSimpleName(final String simpleName) {
        return new MatcherHasSimpleName(equalTo(simpleName));
    }

    public static ReflectionMatcher<FluentMember> hasType(final Class<?> returnType) {
        if (returnType == null) {
            return hasVoidType();
        }
        return new MatcherReturnType(new ConvertClassToReflectedTypeAssignableMatcher().convert(returnType));
    }

    public static ReflectionMatcher<FluentMember> hasType(final FluentClass<?> returnType) {
        if (returnType == null) {
            return hasVoidType();
        }
        return new MatcherReturnType(new ConvertFluentTypeToFluentTypeAssignableMatcher().convert(returnType));
    }

    public static ReflectionMatcher<FluentMember> hasType(final Matcher<? super FluentClass<?>> returnType) {
        return new MatcherReturnType(returnType);
    }

    public static ReflectionMatcher<FluentMember> hasVisibility(final Visibility visibility) {
        return new MatcherVisibility(visibility);
    }

    public static ReflectionMatcher<FluentMember> hasVoidType() {
        return new MatcherReturnType(reflectingOn(void.class));
    }

    public static ReflectionMatcher<FluentClass<?>> isAnInterface() {
        return new MatcherIsInterface();
    }

    public static Matcher<? super FluentMember> isDeclaredByStrictSubtypeOf(final Class<?> klass) {
        return new MatcherDeclaredBy(isStrictSubtypeOf(klass));
    }

    public static ReflectionMatcher<FluentMember> isEqualsMethod() {
        return hasArguments(Object.class).
                and(hasType(boolean.class)).
                and(hasName("equals"));
    }

    public static ReflectionMatcher<FluentMember> isExistence() {
        return isQuery().
                and(hasType(boolean.class).or(hasType(Boolean.class))).
                and(hasNameStartingWith("is").or(hasNameStartingWith("has")));
    }

    public static ReflectionMatcher<FluentMember> isFinal() {
        return new MatcherFinalMember();
    }

    public static ReflectionMatcher<FluentMember> isGetter() {
        return hasNameStartingWith("get").and(isQuery());
    }

    public static ReflectionMatcher<FluentMember> isHashCodeMethod() {
        return hasNoArguments().
                and(hasType(int.class)).
                and(hasName("hashCode"));
    }

    public static ReflectionMatcher<FluentMember> isMutator() {
        return hasArgumentsMatching(anything()).and(
                not(hasNonVoidType()));
    }

    public static ReflectionMatcher<FluentMember> isNotStatic() {
        return not(isStatic());
    }

    public static ReflectionMatcher<FluentMember> isPublicMethod() {
        return new MatcherPublic();
    }

    public static ReflectionMatcher<FluentMember> isQuery() {
        return hasNoArguments().and(not(hasVoidType()));
    }

    public static ReflectionMatcher<FluentField> isReflectingOnField(final Field field) {
        return new MatcherFieldReflectingOn(field);
    }

    public static ReflectionMatcher<FluentMember> isSetter() {
        return hasNameStartingWith("set").and(isMutator());
    }

    public static ReflectionMatcher<FluentMember> isStatic() {
        return new MatcherIsStatic();
    }

    public static Matcher<? super FluentClass<?>> isStrictSubtypeOf(final Class<?> klass) {
        return new MatcherStrictSubtypeOf(klass);
    }

    public static ReflectionMatcher<FluentMember> isToStringMethod() {
        return hasNoArguments().
                and(hasType(String.class)).
                and(hasName("toString"));
    }

    public static <T> ReflectionMatcher<T> not(final ReflectionMatcher<T> matcher) {
        return new ReflectionMatcher<T>() {
            @Override public void describeTo(final Description description) {
                description.appendText("not ").appendDescriptionOf(matcher);
            }

            @Override protected boolean matchesSafely(final T item) {
                return !matcher.matches(item);
            }
        };
    }

    public static ReflectionMatcher<FluentAccess<?>> reflectingOn(final Class<?> klass) {
        return new MatcherReflectingOn(klass);
    }

    public static ReflectionMatcher<FluentConstructor<?>> reflectingOnConstructor(
            final Constructor<?> constructor) {
        return new MatcherConstructorReflectingOn(constructor);
    }
}
