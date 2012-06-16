package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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

public class ReflectionMatchers {
    /**
     * Matches a prefix of the name of a callable
     *
     * @param prefix
     *            the prefix
     *
     * @return true iff the argument is a prefix of the name of the callable
     */
    public static ReflectionMatcher<ReflectedMember> hasNameStartingWith(final String prefix) {
        return new MatcherHasNameStartingWith(prefix);
    }

    /**
     * Matches a suffix of the name of a callable
     *
     * @param suffix
     *            the suffix
     *
     * @return true iff the argument is a suffix of the name of the callable
     */
    public static ReflectionMatcher<ReflectedMember> hasNameEndingWith(final String suffix) {
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
    public static ReflectionMatcher<ReflectedMember> hasNameMatching(final String regex) {
        return new MatcherHasNameMatching(regex);
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
    public static ReflectionMatcher<ReflectedMember> hasNameContaining(final CharSequence substring) {
        return new MatcherHasNameContaining(substring);
    }

    /**
     * Matches the name of a callable
     *
     * @param name
     *            the name
     *
     * @return true iff the argument is equal to the name of the callable
     */
    public static ReflectionMatcher<ReflectedMember> hasName(final String name) {
        return new MatcherNamed(name);
    }

    public static ReflectionMatcher<ReflectedMember> hasPropertyName(final String name) {
        return new MatcherPropertyName(name);
    }

    /**
     * Matches the declaring class of a callable
     *
     * @param declaringClass
     *            the declaring class
     *
     * @return true iff the callable is declared by the argument
     */
    public static ReflectionMatcher<ReflectedMember> declaredBy(final Class<?> declaringClass) {
        return new MatcherDeclaredBy(reflectingOn(declaringClass));
    }

    public static ReflectionMatcher<ReflectedClass<?>> hasNoInterfaces() {
        return new MatcherHasNoInterfaces();
    }

    public static ReflectionMatcher<ReflectedClass<?>> hasNoSuperclasses() {
        return new MatcherHasNoSuperclasses();
    }

    public static ReflectionMatcher<ReflectedClass<?>> hasInterface(final Class<?> interfac3) {
        return new MatcherHasInterface(interfac3);
    }

    public static ReflectionMatcher<ReflectedClass<?>> isAnInterface() {
        return new MatcherIsInterface();
    }

    public static ReflectionMatcher<ReflectedClass<?>> hasSimpleName(final String simpleName) {
        return new MatcherHasSimpleName(equalTo(simpleName));
    }

    public static ReflectionMatcher<ReflectedClass<?>> anyReflectedType() {
        return new MatcherAnyReflectedType();
    }

    public static ReflectionMatcher<ReflectedClass<?>> reflectingOn(final Class<?> klass) {
        return new MatcherReflectingOn(klass);
    }

    public static ReflectionMatcher<ReflectedClass<?>> assignableFrom(final Class<?> klass) {
        return new MatcherAssignableFrom(klass);
    }

    public static ReflectionMatcher<ReflectedClass<?>> assignableFrom(final ReflectedClass<?> klass) {
        return new MatcherAssignableFrom(klass);
    }

    public static ReflectionMatcher<ReflectedConstructor<?>> reflectingOnConstructor(
            final Constructor<?> constructor) {
        return new MatcherConstructorReflectingOn(constructor);
    }

    public static ReflectionMatcher<ReflectedMember> hasArgumentCount(final int argumentCount) {
        return new MatcherArgumentCount(argumentCount);
    }

    public static ReflectionMatcher<ReflectedMember> hasNoArguments() {
        return hasArguments();
    }

    public static ReflectionMatcher<ReflectedMember> hasArguments(final Class<?>... argTypes) {
        return hasArgumentList(asList(argTypes));
    }

    public static ReflectionMatcher<ReflectedMember> hasArgumentList(final List<Class<?>> argTypes) {
        return new MatcherArgumentTypes(convert(argTypes, new ConvertClassToReflectedTypeAssignableMatcher()));
    }

    public static ReflectionMatcher<ReflectedMember> hasArgumentsMatching(
            final Matcher<? super ReflectedClass<?>>... argTypes) {
        return hasArgumentListMatching(asList(argTypes));
    }

    public static ReflectionMatcher<ReflectedMember> hasArgumentListMatching(
            final List<Matcher<? super ReflectedClass<?>>> argTypes) {
        return new MatcherArgumentTypes(argTypes);
    }

    public static ReflectionMatcher<ReflectedMember> hasReflectedArguments(
            final ReflectedClass<?>... argTypes) {
        return hasReflectedArgumentList(asList(argTypes));
    }

    public static ReflectionMatcher<ReflectedMember> hasReflectedArgumentList(
            final List<ReflectedClass<?>> argTypes) {
        return new MatcherArgumentTypes(convert(argTypes, new ConvertReflectedTypeToReflectedTypeAssignableMatcher()));
    }

    public static ReflectionMatcher<ReflectedMember> hasType(final Class<?> returnType) {
        if (returnType == null) {
            return hasVoidType();
        }
        return new MatcherReturnType(new ConvertClassToReflectedTypeAssignableMatcher().convert(returnType));
    }

    public static ReflectionMatcher<ReflectedMember> hasVoidType() {
        return new MatcherReturnType(reflectingOn(void.class));
    }

    public static ReflectionMatcher<ReflectedMember> hasNonVoidType() {
        return not(hasVoidType());
    }

    public static ReflectionMatcher<ReflectedMember> hasType(final ReflectedClass<?> returnType) {
        if (returnType == null) {
            return hasVoidType();
        }
        return new MatcherReturnType(new ConvertReflectedTypeToReflectedTypeAssignableMatcher().convert(returnType));
    }

    public static ReflectionMatcher<ReflectedMember> hasType(final Matcher<? super ReflectedClass<?>> returnType) {
        return new MatcherReturnType(returnType);
    }

    public static ReflectionMatcher<ReflectedAnnotated> annotatedWith(final Class<? extends Annotation> annotation) {
        return new MatcherCallableAnnotatedWith(annotation);
    }

    public static ReflectionMatcher<ReflectedMember> isStatic() {
        return new MatcherIsStatic();
    }

    public static ReflectionMatcher<ReflectedMember> isNotStatic() {
        return not(isStatic());
    }

    public static <T> ReflectionMatcher<T> not(final ReflectionMatcher<T> matcher) {
        return new ReflectionMatcher<T>() {
            @Override protected boolean matchesSafely(final T item) {
                return !matcher.matches(item);
            }

            @Override public void describeTo(final Description description) {
                description.appendText("not ").appendDescriptionOf(matcher);
            }
        };
    }

    public static ReflectionMatcher<ReflectedMember> isQuery() {
        return hasNoArguments().and(not(hasVoidType()));
    }

    public static ReflectionMatcher<ReflectedMember> isGetter() {
        return hasNameStartingWith("get").and(isQuery());
    }

    public static ReflectionMatcher<ReflectedMember> isMutator() {
        return hasArgumentsMatching(anything()).and(
                not(hasNonVoidType()));
    }

    public static ReflectionMatcher<ReflectedMember> isSetter() {
        return hasNameStartingWith("set").and(isMutator());
    }

    public static ReflectionMatcher<ReflectedMember> isExistence() {
        return isQuery().
                and(hasType(boolean.class).or(hasType(Boolean.class))).
                and(hasNameStartingWith("is").or(hasNameStartingWith("has")));
    }

    public static ReflectionMatcher<ReflectedMember> isHashCodeMethod() {
        return hasNoArguments().
                and(hasType(int.class)).
                and(hasName("hashCode"));
    }

    public static ReflectionMatcher<ReflectedMember> isEqualsMethod() {
        return hasArguments(Object.class).
                and(hasType(boolean.class)).
                and(hasName("equals"));
    }

    public static ReflectionMatcher<ReflectedMember> isToStringMethod() {
        return hasNoArguments().
                and(hasType(String.class)).
                and(hasName("toString"));
    }

    public static ReflectionMatcher<ReflectedMember> isPublicMethod() {
        return new MatcherPublic();
    }

    public static Matcher<? super ReflectedMember> isDeclaredByStrictSubtypeOf(final Class<?> klass) {
        return new MatcherDeclaredBy(isStrictSubtypeOf(klass));
    }

    public static Matcher<? super ReflectedClass<?>> isStrictSubtypeOf(final Class<?> klass) {
        return new MatcherStrictSubtypeOf(klass);
    }

    public static ReflectionMatcher<ReflectedField> isReflectingOnField(final Field field) {
        return new MatcherFieldReflectingOn(field);
    }

    public static ReflectionMatcher<ReflectedMember> isFinal() {
        return new MatcherFinalMember();
    }

    public static ReflectionMatcher<ReflectedMember> hasVisibility(final Visibility visibility) {
        return new MatcherVisibility(visibility);
    }
}
