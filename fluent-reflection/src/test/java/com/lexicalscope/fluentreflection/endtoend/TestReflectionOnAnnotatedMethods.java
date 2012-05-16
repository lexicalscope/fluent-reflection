package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.FluentReflection.*;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.lang.annotation.Retention;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.lexicalscope.fluentreflection.ReflectedMethod;

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

public class TestReflectionOnAnnotatedMethods {
    @Retention(RUNTIME) @interface MyAnnotation {
        String value();
    }

    class ClassWithAnnotatedMethod {
        @MyAnnotation(value = "my value") void myAnnotatedMethod() {};
        void myNonAnnotatedMethod() {};
    }

    private final ReflectedMethod annotatedMethod = type(ClassWithAnnotatedMethod.class).method(
            hasName("myAnnotatedMethod"));

    private final ReflectedMethod boundAnnotatedMethod = object(new ClassWithAnnotatedMethod()).method(
            hasName("myAnnotatedMethod"));

    private final ReflectedMethod nonAnnotatedMethod = type(ClassWithAnnotatedMethod.class).method(
            hasName("myNonAnnotatedMethod"));

    private final ReflectedMethod boundNonAnnotatedMethod = type(ClassWithAnnotatedMethod.class).method(
            hasName("myNonAnnotatedMethod"));

    @Test public void canReadAnnotationOnMethod() throws Exception {
        assertThat(annotatedMethod.annotation(MyAnnotation.class).value(), equalTo("my value"));
        assertThat(boundAnnotatedMethod.annotation(MyAnnotation.class).value(), equalTo("my value"));
    }

    @Test public void whenMethodHasAnnotationAnnotatedWithReturnsTrue() {
        assertThat(annotatedMethod.annotatedWith(MyAnnotation.class), equalTo(true));
        assertThat(boundAnnotatedMethod.annotatedWith(MyAnnotation.class), equalTo(true));
    }

    @Test public void whenMethodDoesNotHaveAnnotationAnnotatedWithReturnsFalse() {
        assertThat(nonAnnotatedMethod.annotatedWith(MyAnnotation.class), equalTo(false));
        assertThat(boundNonAnnotatedMethod.annotatedWith(MyAnnotation.class), equalTo(false));
    }

    @Test public void matchingAnnotationIsReflectedOn() {
        assertThat(annotatedMethod.annotation(Matchers.anything()), hasSimpleName("MyAnnotation"));
        assertThat(boundAnnotatedMethod.annotation(Matchers.anything()), hasSimpleName("MyAnnotation"));
    }
}
