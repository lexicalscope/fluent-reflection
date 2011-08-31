package com.lexicalscope.fluentreflection.matchers;

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

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectedType;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class ReflectionMatchers {
    public static ReflectionMatcher<ReflectedMethod> methodHasNameStartingWith(final String prefix) {
        return new MethodHasNameStartingWith(prefix);
    }

    public static ReflectionMatcher<ReflectedMethod> methodHasNameEndingWith(final String suffix) {
        return new MethodHasNameEndingWith(suffix);
    }

    public static ReflectionMatcher<ReflectedMethod> methodHasNameMatching(final String regex) {
        return new MethodHasNameMatching(regex);
    }

    public static ReflectionMatcher<ReflectedMethod> methodHasNameContaining(final CharSequence substring) {
        return new MethodHasNameContaining(substring);
    }

    public static ReflectionMatcher<ReflectedMethod> methodNamed(final String name) {
        return new MethodNamed(name);
    }

    public static ReflectionMatcher<ReflectedMethod> methodWithArguments(final Class<?>... expectedArgumentTypes) {
        return new MethodWithArguments(expectedArgumentTypes);
    }

    public static ReflectionMatcher<ReflectedMethod> methodDeclaredBy(final Class<?> declaringKlass) {
        return new MethodDeclaredBy(declaringKlass);
    }

    public static ReflectionMatcher<ReflectedType<?>> typeHasNoInterfaces() {
        return new TypeHasNoInterfaces();
    }

    public static ReflectionMatcher<ReflectedType<?>> typeHasNoSuperclasses() {
        return new TypeHasNoSuperclasses();
    }

    public static ReflectionMatcher<ReflectedType<?>> typeHasInterface(final Class<?> interfac3) {
        return new TypeHasInterface(interfac3);
    }

    public static ReflectionMatcher<ReflectedType<?>> typeIsInterface() {
        return new TypeIsInterface();
    }

    public static ReflectionMatcher<ReflectedType<?>> reflectedTypeReflectingOn(final Class<?> klass) {
        return new ReflectedTypeReflectingOn(klass);
    }
}
