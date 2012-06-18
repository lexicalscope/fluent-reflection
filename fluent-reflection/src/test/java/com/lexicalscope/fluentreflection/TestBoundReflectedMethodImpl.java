package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.method;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectingOn;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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

public class TestBoundReflectedMethodImpl {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    static class ClassWithStaticMethod {
        static void method() {
        }
    }

    static class ClassWithMethod {
        Integer method() {
            return 42;
        }

        @Override
        public String toString() {
            return "my toString";
        }
    }

    private final BoundReflectedMethodImpl methodWithReturnType;

    public TestBoundReflectedMethodImpl() throws SecurityException, NoSuchMethodException {
        methodWithReturnType = new BoundReflectedMethodImpl(
                null,
                method(ClassWithMethod.class.getDeclaredMethod("method")),
                new ClassWithMethod());
    }

    @Test
    public void bindingStaticMethodIsNotAllowed() throws Exception {
        exception.expect(IllegalArgumentException.class);

        new BoundReflectedMethodImpl(
                null,
                method(ClassWithStaticMethod.class.getDeclaredMethods()[0]),
                new ClassWithStaticMethod());
    }

    @Test
    public void boundMethodToStringIsAllowed() throws Exception {
        assertThat(
                methodWithReturnType,
                hasToString(containsString("method() in my toString")));
    }

    @Test
    public void boundMethodReturnTypeIsAvailable() throws Exception {
        assertThat(
                methodWithReturnType.type(),
                reflectingOn(Integer.class));
    }
}
