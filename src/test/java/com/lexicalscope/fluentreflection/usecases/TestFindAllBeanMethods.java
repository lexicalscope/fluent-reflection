package com.lexicalscope.fluentreflection.usecases;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matcher;
import org.junit.Test;

import com.lexicalscope.fluentreflection.ListBuilder;
import com.lexicalscope.fluentreflection.ReflectedCallable;

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

public class TestFindAllBeanMethods {
    static class Bean {
        private int readWriteProperty;
        private String readOnlyProperty;
        private Object writeOnlyProperty;

        String getReadOnlyProperty() {
            return readOnlyProperty;
        }

        void setReadOnlyProperty(final String readOnlyProperty) {
            this.readOnlyProperty = readOnlyProperty;
        }

        int getReadWriteProperty() {
            return readWriteProperty;
        }

        void setWriteOnlyProperty(final Object writeOnlyProperty) {
            this.writeOnlyProperty = writeOnlyProperty;
        }

        int getMethodWithArgument(final int outOfPlace) {
            return outOfPlace;
        }

        void getMethodReturningVoid() {
        }
    }

    @Test
    public void canSelectAllGetters() throws Exception {
        assertThat(
                object(new Bean()).methods(
                        methodHasNameStartingWith("get").and(callableHasNoArguments()).and(
                                not(callableHasVoidReturn()))),
                containsInAnyOrder(
                        ListBuilder.<Matcher<? super ReflectedCallable>>
                                list(methodHasName("getReadOnlyProperty")).add(
                                        methodHasName("getReadWriteProperty")).$()));
    }
}
