package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;

import java.util.List;

import org.junit.Test;

import com.lexicalscope.fluentreflection.TypeToken;

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

public class TestReflectedMethodToString {
    static class ClassWithMethods<T> {
        void myMethod() {};
        public void myPublicMethod() {};
        @SuppressWarnings("unused") private void myPrivateMethod() {};
        protected void myProtectedMethod() {};
        final void myFinalMethod() {};
        static void myStaticMethod() {};
        List<String> myMethodWithGenericReturnType() {
            return null;
        };
        List<T> myMethodWithParameterizedGenericReturnType() {
            return null;
        };
        <S> List<S> myGenericMethodWithParameterizedGenericReturnType() {
            return null;
        };
        void myMethodWithOneArgument(final String argument) {};
        void myMethodWithTwoArguments(final String argument0, final String argument1) {};
        void myMethodWithGenericArgument(final List<String> argument) {};
        void myMethodWithParameterizedGenericArgument(final List<T> argument) {};
        <S> void myGenericMethodWithParameterizedGenericArgument(final List<S> argument) {};
    }

    @Test public void voidMethodWithNoArguments() {
        assertThat(type(ClassWithMethods.class).method("myMethod"), hasToString("void myMethod()"));
    }

    @Test public void publicMethod() {
        assertThat(type(ClassWithMethods.class).method("myPublicMethod"), hasToString("public void myPublicMethod()"));
    }

    @Test public void privateMethod() {
        assertThat(
                type(ClassWithMethods.class).method("myPrivateMethod"),
                hasToString("private void myPrivateMethod()"));
    }

    @Test public void protectedMethod() {
        assertThat(
                type(ClassWithMethods.class).method("myProtectedMethod"),
                hasToString("protected void myProtectedMethod()"));
    }

    @Test public void finalMethod() {
        assertThat(
                type(ClassWithMethods.class).method("myFinalMethod"),
                hasToString("final void myFinalMethod()"));
    }

    @Test public void staticMethod() {
        assertThat(
                type(ClassWithMethods.class).method("myStaticMethod"),
                hasToString("static void myStaticMethod()"));
    }

    @Test public void methodWithGenericReturnType() {
        assertThat(
                type(ClassWithMethods.class).method("myMethodWithGenericReturnType"),
                hasToString("java.util.List<java.lang.String> myMethodWithGenericReturnType()"));
    }

    @Test public void methodWithBoundParameterizedGenericReturnType() {
        assertThat(
                type(new TypeToken<ClassWithMethods<Integer>>() {})
                        .method("myMethodWithParameterizedGenericReturnType"),
                hasToString("java.util.List<java.lang.Integer> myMethodWithParameterizedGenericReturnType()"));
    }

    @Test public void methodWithParameterizedGenericReturnType() {
        assertThat(
                type(ClassWithMethods.class)
                        .method("myMethodWithParameterizedGenericReturnType"),
                hasToString("java.util.List<T> myMethodWithParameterizedGenericReturnType()"));
    }

    @Test public void parameterizedGenericMethodWithGenericReturnType() {
        assertThat(
                type(ClassWithMethods.class)
                        .method("myGenericMethodWithParameterizedGenericReturnType"),
                hasToString("<S> java.util.List<S> myGenericMethodWithParameterizedGenericReturnType()"));
    }

    @Test public void methodWithOneArgument() {
        assertThat(
                type(ClassWithMethods.class)
                        .method("myMethodWithOneArgument"),
                hasToString("void myMethodWithOneArgument(java.lang.String)"));
    }

    @Test public void methodWithTwoArguments() {
        assertThat(
                type(ClassWithMethods.class)
                        .method("myMethodWithTwoArguments"),
                hasToString("void myMethodWithTwoArguments(java.lang.String, java.lang.String)"));
    }

    @Test public void methodWithGenericArgument() {
        assertThat(
                type(ClassWithMethods.class)
                        .method("myMethodWithGenericArgument"),
                hasToString("void myMethodWithGenericArgument(java.util.List<java.lang.String>)"));
    }

    @Test public void methodWithBoundParameterizedGenericArgument() {
        assertThat(
                type(new TypeToken<ClassWithMethods<Integer>>() {})
                        .method("myMethodWithParameterizedGenericArgument"),
                hasToString("void myMethodWithParameterizedGenericArgument(java.util.List<java.lang.Integer>)"));
    }

    @Test public void methodWithParameterizedGenericReturnArgument() {
        assertThat(
                type(ClassWithMethods.class)
                        .method("myMethodWithParameterizedGenericArgument"),
                hasToString("void myMethodWithParameterizedGenericArgument(java.util.List<T>)"));
    }

    @Test public void genericMethodWithParameterizedGenericReturnArgument() {
        assertThat(
                type(new TypeToken<ClassWithMethods<Integer>>() {})
                        .method("myGenericMethodWithParameterizedGenericArgument"),
                hasToString("<S> void myGenericMethodWithParameterizedGenericArgument(java.util.List<S>)"));
    }
}
