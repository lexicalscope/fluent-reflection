package com.lexicalscope.fluentreflection;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.jmock.Expectations;

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

public class TestMatcherConstructorReflectingOn extends AbstractTestReflectionMatcher<FluentConstructor<?>> {
    static class ClassWithAConstructor {
        ClassWithAConstructor() {}
    }

    static class AnotherClassWithAConstructor {
        AnotherClassWithAConstructor() {}
    }

    @Override protected FluentConstructor<?> target() {
        return constructor;
    }

    @Override protected void setupMatchingCase() throws Throwable {
        context.checking(new Expectations() {
            {
                oneOf(constructor).member();
                will(returnValue(ClassWithAConstructor.class.getDeclaredConstructor()));
            }
        });
    }

    @Override protected void setupFailingCase() throws Throwable {
        context.checking(new Expectations() {
            {
                oneOf(constructor).member();
                will(returnValue(AnotherClassWithAConstructor.class.getDeclaredConstructor()));
            }
        });
    }

    @Override protected Matcher<String> hasDescription() {
        return equalTo("reflecting on constructor <com.lexicalscope.fluentreflection.TestMatcherConstructorReflectingOn$ClassWithAConstructor()>");
    }

    @Override protected ReflectionMatcher<FluentConstructor<?>> matcher() throws Throwable {
        return new MatcherConstructorReflectingOn(ClassWithAConstructor.class.getDeclaredConstructor());
    }
}
