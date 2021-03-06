package com.lexicalscope.fluentreflection.usecases;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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

public class TestCanReadAndSetFields {
    public static class AnnotatedFields {
        public String fieldPublic;
        String fieldPackage;
        protected String fieldProtected;
        private String fieldPrivate;
    }

    private final AnnotatedFields subject = new AnnotatedFields();

    @Test public void canSetPublicField() throws Exception {
        object(subject).field("fieldPublic").call("value");

        assertThat(subject.fieldPublic, equalTo("value"));
    }

    @Test public void canReadPublicField() throws Exception {
        subject.fieldPublic = "value";

        final String value = object(subject).field("fieldPublic").call().as(String.class);

        assertThat(value, equalTo("value"));
    }

    @Test public void canSetPackageField() throws Exception {
        object(subject).field("fieldPackage").call("value");

        assertThat(subject.fieldPackage, equalTo("value"));
    }

    @Test public void canReadPackageField() throws Exception {
        subject.fieldPackage = "value";

        final String value = object(subject).field("fieldPackage").call().as(String.class);

        assertThat(value, equalTo("value"));
    }

    @Test public void canSetProtectedField() throws Exception {
        object(subject).field("fieldProtected").call("value");

        assertThat(subject.fieldProtected, equalTo("value"));
    }

    @Test public void canReadProtectedField() throws Exception {
        subject.fieldProtected = "value";

        final String value = object(subject).field("fieldProtected").call().as(String.class);

        assertThat(value, equalTo("value"));
    }

    @Test public void canSetPrivateField() throws Exception {
        object(subject).field("fieldPrivate").call("value");

        assertThat(subject.fieldPrivate, equalTo("value"));
    }

    @Test public void canReadPrivateField() throws Exception {
        subject.fieldPrivate = "value";

        final String value = object(subject).field("fieldPrivate").call().as(String.class);

        assertThat(value, equalTo("value"));
    }
}
