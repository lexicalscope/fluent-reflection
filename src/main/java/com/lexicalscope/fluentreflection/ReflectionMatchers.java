package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.anything;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
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
    public static ReflectionMatcher<ReflectedCallable> callableHasNameStartingWith(final String prefix) {
        return new MatcherCallableHasNameStartingWith(prefix);
    }

    /**
     * Matches a suffix of the name of a callable
     * 
     * @param suffix
     *            the suffix
     * 
     * @return true iff the argument is a suffix of the name of the callable
     */
    public static ReflectionMatcher<ReflectedCallable> callableHasNameEndingWith(final String suffix) {
        return new MatcherCallableHasNameEndingWith(suffix);
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
    public static ReflectionMatcher<ReflectedCallable> callableHasNameMatching(final String regex) {
        return new MatcherCallableHasNameMatching(regex);
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
    public static ReflectionMatcher<ReflectedCallable> callableHasNameContaining(final CharSequence substring) {
        return new MatcherCallableHasNameContaining(substring);
    }

    /**
     * Matches the name of a callable
     * 
     * @param name
     *            the name
     * 
     * @return true iff the argument is equal to the name of the callable
     */
    public static ReflectionMatcher<ReflectedCallable> callableHasName(final String name) {
        return new MatcherCallableNamed(name);
    }

    /**
     * Matches the declaring class of a callable
     * 
     * @param declaringClass
     *            the declaring class
     * 
     * @return true iff the callable is declared by the argument
     */
    public static ReflectionMatcher<ReflectedCallable> callableDeclaredBy(final Class<?> declaringClass) {
        return new MatcherCallableDeclaredBy(declaringClass);
    }

    public static ReflectionMatcher<ReflectedClass<?>> typeHasNoInterfaces() {
        return new MatcherTypeHasNoInterfaces();
    }

    public static ReflectionMatcher<ReflectedClass<?>> typeHasNoSuperclasses() {
        return new MatcherTypeHasNoSuperclasses();
    }

    public static ReflectionMatcher<ReflectedClass<?>> typeHasInterface(final Class<?> interfac3) {
        return new MatcherTypeHasInterface(interfac3);
    }

    public static ReflectionMatcher<ReflectedClass<?>> typeIsInterface() {
        return new MatcherTypeIsInterface();
    }

    public static ReflectionMatcher<ReflectedClass<?>> anyReflectedType() {
        return new MatcherAnyReflectedType();
    }

    public static ReflectionMatcher<ReflectedClass<?>> reflectedTypeReflectingOn(final Class<?> klass) {
        return new MatcherReflectedTypeReflectingOn(klass);
    }

    public static ReflectionMatcher<ReflectedClass<?>> reflectedTypeReflectingOnAssignableFrom(final Class<?> klass) {
        return new MatcherReflectedTypeReflectingOnAssignableFrom(klass);
    }

    public static ReflectionMatcher<ReflectedClass<?>> reflectedTypeReflectingOnAssignableFrom(
            final ReflectedClass<?> klass) {
        return new MatcherReflectedTypeReflectingOnAssignableFrom(klass);
    }

    public static ReflectionMatcher<ReflectedConstructor<?>> reflectedConstructorReflectingOn(
            final Constructor<?> constructor) {
        return new MatcherConstructorReflectingOn(constructor);
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasThisManyArguments(final int argumentCount) {
        return new MatcherArgumentCount(argumentCount);
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasNoArguments() {
        return callableHasArguments();
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasArguments(final Class<?>... argTypes) {
        return callableHasArgumentList(asList(argTypes));
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasArgumentList(final List<Class<?>> argTypes) {
        return new MatcherArgumentTypes(convert(argTypes, new ConvertClassToReflectedTypeAssignableMatcher()));
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasArgumentsMatching(
            final Matcher<? super ReflectedClass<?>>... argTypes) {
        return callableHasArgumentListMatching(asList(argTypes));
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasArgumentListMatching(
            final List<Matcher<? super ReflectedClass<?>>> argTypes) {
        return new MatcherArgumentTypes(argTypes);
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasReflectedArguments(
            final ReflectedClass<?>... argTypes) {
        return callableHasReflectedArgumentList(asList(argTypes));
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasReflectedArgumentList(
            final List<ReflectedClass<?>> argTypes) {
        return new MatcherArgumentTypes(convert(argTypes, new ConvertReflectedTypeToReflectedTypeAssignableMatcher()));
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasReturnType(final Class<?> returnType) {
        if (returnType == null) {
            return callableHasVoidReturn();
        }
        return new MatcherReturnType(new ConvertClassToReflectedTypeAssignableMatcher().convert(returnType));
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasVoidReturn() {
        return new MatcherReturnType(reflectedTypeReflectingOn(void.class));
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasNonVoidReturn() {
        return not(callableHasVoidReturn());
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasReturnType(final ReflectedClass<?> returnType) {
        if (returnType == null) {
            return callableHasVoidReturn();
        }
        return new MatcherReturnType(new ConvertReflectedTypeToReflectedTypeAssignableMatcher().convert(returnType));
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasReturnType(
            final Matcher<? super ReflectedClass<?>> returnType) {
        return new MatcherReturnType(returnType);
    }

    public static Matcher<ReflectedAnnotated> annotatedWith(final Class<? extends Annotation> annotation) {
        return new MatcherCallableAnnotatedWith(annotation);
    }

    public static ReflectionMatcher<ReflectedMethod> methodIsStatic() {
        return new MatcherMethodIsStatic();
    }

    public static ReflectionMatcher<ReflectedMethod> methodIsNotStatic() {
        return not(methodIsStatic());
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

    public static ReflectionMatcher<ReflectedCallable> isQuery() {
        return callableHasNoArguments().and(not(callableHasVoidReturn()));
    }

    public static ReflectionMatcher<ReflectedCallable> isGetter() {
        return callableHasNameStartingWith("get").and(isQuery());
    }

    public static ReflectionMatcher<ReflectedCallable> isMutator() {
        return callableHasArgumentsMatching(anything()).and(
                not(callableHasNonVoidReturn()));
    }

    public static ReflectionMatcher<ReflectedCallable> isSetter() {
        return callableHasNameStartingWith("set").and(isMutator());
    }
}
