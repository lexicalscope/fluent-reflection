package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.constructor;
import static org.hamcrest.Matchers.equalTo;

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

public class TestMatcherConstructorReflectingOn extends AbstractTestReflectionMatcherNoMocks<FluentConstructor<?>> {
    static class ClassWithAConstructor {
        ClassWithAConstructor() {}
    }

    static class AnotherClassWithAConstructor {
        AnotherClassWithAConstructor() {}
    }

    @Override protected FluentConstructor<?> target() {
        return constructor(ClassWithAConstructor.class);
    }

    @Override protected FluentConstructor<?> failingTarget() {
        return constructor(AnotherClassWithAConstructor.class);
    }

    @Override protected Matcher<String> hasDescription() {
        return equalTo("reflecting on constructor <com.lexicalscope.fluentreflection.TestMatcherConstructorReflectingOn$ClassWithAConstructor()>");
    }

    @Override protected ReflectionMatcher<FluentConstructor<?>> matcher() throws Throwable {
        return new MatcherConstructorReflectingOn(ClassWithAConstructor.class.getDeclaredConstructor());
    }
}
