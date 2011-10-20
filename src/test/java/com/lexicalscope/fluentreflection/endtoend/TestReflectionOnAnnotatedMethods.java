package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.lang.annotation.Retention;

import org.hamcrest.Matchers;
import org.junit.Test;

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
    @Retention(RUNTIME) public @interface MyAnnotation {
        String value();
    }

    public interface InterfaceWithAnnotatedMethod {
        @MyAnnotation(value = "my value") void myAnnotatedMethod();
        void myNonAnnotatedMethod();
    }

    @Test public void canReadAnnotationOnMethod() throws Exception {
        assertThat(type(InterfaceWithAnnotatedMethod.class)
                .method(callableHasName("myAnnotatedMethod"))
                .annotation(MyAnnotation.class).value(), equalTo("my value"));
    }

    @Test public void whenMethodHasAnnotationAnnotatedWithReturnsTrue() {
        assertThat(type(InterfaceWithAnnotatedMethod.class)
                .method(callableHasName("myAnnotatedMethod"))
                .annotatedWith(MyAnnotation.class), equalTo(true));
    }

    @Test public void whenMethodDoesNotHaveAnnotationAnnotatedWithReturnsFalse() {
        assertThat(type(InterfaceWithAnnotatedMethod.class)
                .method(callableHasName("myNonAnnotatedMethod"))
                .annotatedWith(MyAnnotation.class), equalTo(false));
    }

    @Test public void matchingAnnotationIsReflectedOn() {
        assertThat(type(InterfaceWithAnnotatedMethod.class)
                .method(callableHasName("myAnnotatedMethod"))
                .annotation(Matchers.anything()), typeHasSimpleName("MyAnnotation"));
    }
}
