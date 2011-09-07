package com.lexicalscope.fluentreflection;

import java.lang.reflect.Constructor;

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
    public static ReflectionMatcher<ReflectedMethod> methodHasNameStartingWith(final String prefix) {
        return new MatcherMethodHasNameStartingWith(prefix);
    }

    public static ReflectionMatcher<ReflectedMethod> methodHasNameEndingWith(final String suffix) {
        return new MatcherMethodHasNameEndingWith(suffix);
    }

    public static ReflectionMatcher<ReflectedMethod> methodHasNameMatching(final String regex) {
        return new MatcherMethodHasNameMatching(regex);
    }

    public static ReflectionMatcher<ReflectedMethod> methodHasNameContaining(final CharSequence substring) {
        return new MatcherMethodHasNameContaining(substring);
    }

    public static ReflectionMatcher<ReflectedMethod> methodNamed(final String name) {
        return new MatcherMethodNamed(name);
    }

    public static ReflectionMatcher<ReflectedMethod> methodWithArguments(final Class<?>... expectedArgumentTypes) {
        return new MatcherMethodWithArguments(expectedArgumentTypes);
    }

    public static ReflectionMatcher<ReflectedMethod> methodDeclaredBy(final Class<?> declaringKlass) {
        return new MatcherMethodDeclaredBy(declaringKlass);
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

    public static ReflectionMatcher<ReflectedType<?>> reflectedTypeReflectingOn(final Class<?> klass) {
        return new MatcherReflectedTypeReflectingOn(klass);
    }

    public static ReflectionMatcher<ReflectedConstructor<?>> reflectedConstructorReflectingOn(final Constructor<?> constructor) {
        return new MatcherConstructorReflectingOn(constructor);
    }

    public static ReflectionMatcher<ReflectedCallable> callableHasThisManyArguments(final int argumentCount) {
        return new MatcherArgumentCount(argumentCount);
    }

    public static ReflectionMatcher<ReflectedConstructor<?>> constructorHasArguments(final Class<?>... argTypes) {
        return new MatcherArgumentTypes(argTypes);
    }
}
