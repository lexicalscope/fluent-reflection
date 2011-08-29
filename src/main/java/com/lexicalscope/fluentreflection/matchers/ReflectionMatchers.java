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

import static ch.lambdaj.Lambda.*;


import org.hamcrest.Description;
import org.hamcrest.Matcher;

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

    public static ReflectionMatcher<ReflectedMethod> declaredBy(final Class<?> declaringKlass) {
        return new ReflectionMatcher<ReflectedMethod>() {
            @Override
            public boolean matchesSafely(final ReflectedMethod arg) {
                return arg.getDeclaringClass().getClassUnderReflection().equals(declaringKlass);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("method declared by ").appendValue(declaringKlass);
            }
        };
    }

    public static Matcher<ReflectedType<?>> hasNoInterfaces() {
        return new ReflectionMatcher<ReflectedType<?>>() {
            @Override
            public boolean matchesSafely(final ReflectedType<?> arg) {
                return arg.getInterfaces().isEmpty();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("type that implements no interfaces");
            }
        };
    }

    public static Matcher<ReflectedType<?>> hasInterface(final Class<?> interfac3) {
        return new ReflectionMatcher<ReflectedType<?>>() {
            @Override
            public boolean matchesSafely(final ReflectedType<?> arg) {
                return select(
                        arg.getInterfaces(),
                        reflectingOn(interfac3)).isEmpty();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("type that implements no interfaces");
            }
        };
    }

    public static Matcher<ReflectedType<?>> isInterface() {
        return new ReflectionMatcher<ReflectedType<?>>() {
            @Override
            public boolean matchesSafely(final ReflectedType<?> arg) {
                return arg.isInterface();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("type that is an interface");
            }
        };
    }

    public static Matcher<ReflectedType<?>> reflectingOn(final Class<?> klass) {
        return new ReflectionMatcher<ReflectedType<?>>() {
            @Override
            public boolean matchesSafely(final ReflectedType<?> arg) {
                return arg.getClassUnderReflection().equals(klass);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("reflecting on type ").appendValue(klass);
            }
        };
    }
}
