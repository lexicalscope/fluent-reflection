package com.lexicalscope.fluentreflection.usecases;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

import javax.inject.Inject;

import org.junit.Test;

/*
 * Copyright 2012 Tim Wood
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

public class TestCanFindAllAnnotatedFields {
    public static class AnnotatedFields {
        public String field0;
        String field1;
        protected String field2;
        @SuppressWarnings("unused") private String field3;

        @Inject public String annotatedField0;
        @Inject String annotatedField1;
        @Inject protected String annotatedField2;
        @SuppressWarnings("unused") @Inject private String annotatedField3;
    }

    private final AnnotatedFields subject = new AnnotatedFields();

    @SuppressWarnings("unchecked") @Test
    public void canSelectAllFieldThatAreAnnotated() throws Exception {
        assertThat(
                object(subject).fields(annotatedWith(Inject.class)),
                containsInAnyOrder(
                        hasName("annotatedField0"),
                        hasName("annotatedField1"),
                        hasName("annotatedField2"),
                        hasName("annotatedField3")));
    }
}
