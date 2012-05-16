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

        String getReadOnlyProperty() {
            return readOnlyProperty;
        }

        int getReadWriteProperty() {
            return readWriteProperty;
        }

        void setReadWriteProperty(final int readWriteProperty) {
            this.readWriteProperty = readWriteProperty;
        }

        void setWriteOnlyProperty(final Object writeOnlyProperty) {
        // do something with it
        }

        int getMethodWithArgument(final int outOfPlace) {
            return outOfPlace;
        }

        void getMethodReturningVoid() {}
    }

    // TODO[tim]: test "is" prefix

    @Test public void canSelectAllGetters() throws Exception {
        assertThat(
                object(new Bean()).methods(isGetter()),
                containsInAnyOrder(
                        ListBuilder.<Matcher<? super ReflectedCallable>>
                                list(hasName("getReadOnlyProperty")).add(
                                        hasName("getReadWriteProperty")).$()));
    }

    @Test public void canSelectAllSetters() throws Exception {
        assertThat(
                object(new Bean()).methods(
                        isSetter()),
                containsInAnyOrder(
                        ListBuilder.<Matcher<? super ReflectedCallable>>
                                list(hasName("setWriteOnlyProperty")).add(
                                        hasName("setReadWriteProperty")).$()));
    }
}
