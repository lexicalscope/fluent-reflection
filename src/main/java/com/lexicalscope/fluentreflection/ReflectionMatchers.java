package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;
import static java.util.Arrays.asList;

import java.lang.reflect.Constructor;
import java.util.List;

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
    public static ReflectionMatcher<ReflectedCallable> methodHasNameStartingWith(final String prefix) {
        return new MatcherCallableHasNameStartingWith(prefix);
    }

    public static ReflectionMatcher<ReflectedCallable> methodHasNameEndingWith(final String suffix) {
        return new MatcherCallableHasNameEndingWith(suffix);
    }

    public static ReflectionMatcher<ReflectedCallable> methodHasNameMatching(final String regex) {
        return new MatcherCallableHasNameMatching(regex);
    }

    public static ReflectionMatcher<ReflectedCallable> methodHasNameContaining(final CharSequence substring) {
        return new MatcherCallableHasNameContaining(substring);
    }

    public static ReflectionMatcher<ReflectedCallable> methodNamed(final String name) {
        return new MatcherCallableNamed(name);
    }

    public static ReflectionMatcher<ReflectedCallable> methodWithArguments(final Class<?>... expectedArgumentTypes) {
        return new MatcherCallableWithArguments(expectedArgumentTypes);
    }

    public static ReflectionMatcher<ReflectedCallable> methodDeclaredBy(final Class<?> declaringKlass) {
        return new MatcherCallableDeclaredBy(declaringKlass);
    }

    public static ReflectionMatcher<ReflectedType<?>> typeHasNoInterfaces() {
        return new MatcherTypeHasNoInterfaces();
    }

    public static ReflectionMatcher<ReflectedType<?>> typeHasNoSuperclasses() {
        return new MatcherTypeHasNoSuperclasses();
    }

    public static ReflectionMatcher<ReflectedType<?>> typeHasInterface(final Class<?> interfac3) {
        return new MatcherTypeHasInterface(interfac3);
    }

    public static ReflectionMatcher<ReflectedType<?>> typeIsInterface() {
        return new MatcherTypeIsInterface();
    }

    public static ReflectionMatcher<ReflectedType<?>> anyReflectedType() {
        return new MatcherAnyReflectedType();
    }

    public static ReflectionMatcher<ReflectedType<?>> reflectedTypeReflectingOn(final Class<?> klass) {
        return new MatcherReflectedTypeReflectingOn(klass);
    }

    public static ReflectionMatcher<ReflectedType<?>> reflectedTypeReflectingOnAssignableFrom(final Class<?> klass) {
        return new MatcherReflectedTypeReflectingOnAssignableFrom(klass);
    }

    public static ReflectionMatcher<ReflectedConstructor<?>> reflectedConstructorReflectingOn(
            final Constructor<?> constructor) {
        return new MatcherConstructorReflectingOn(constructor);
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasThisManyArguments(final int argumentCount) {
        return new MatcherArgumentCount(argumentCount);
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasArguments(final Class<?>... argTypes) {
        return callableHasArguments(asList(argTypes));
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasArguments(final List<Class<?>> argTypes) {
        return new MatcherArgumentTypes(convert(argTypes, new ConvertClassToReflectedTypeAssignableMatcher()));
    }
}
