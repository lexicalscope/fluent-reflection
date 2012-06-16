package com.lexicalscope.fluentreflection.usecases;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.lexicalscope.fluentreflection.Visibility;

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

public class TestIdentifyMembersByVisibiity {
    public static class AnnotatedFields {
        public String fieldPublic;
        String fieldPackage;
        protected String fieldProtected;
        private String fieldPrivate;

        String getFieldPublic() {
            return fieldPublic;
        }
        String getFieldPackage() {
            return fieldPackage;
        }
        String getFieldProtected() {
            return fieldProtected;
        }
        String getFieldPrivate() {
            return fieldPrivate;
        }
    }

    private final AnnotatedFields subject = new AnnotatedFields();

    @Test public void canMatchPublicFieldVisibility() throws Exception {
        assertThat(object(subject).field(hasName("fieldPublic")), hasVisibility(Visibility.PUBLIC));
    }

    @Test public void canMatchPackageFieldVisibility() throws Exception {
        assertThat(object(subject).field(hasName("fieldPackage")), hasVisibility(Visibility.DEFAULT));
    }

    @Test public void canMatchProtectedFieldVisibility() throws Exception {
        assertThat(object(subject).field(hasName("fieldProtected")), hasVisibility(Visibility.PROTECTED));
    }

    @Test public void canMatchPrivateFieldVisibility() throws Exception {
        assertThat(object(subject).field(hasName("fieldPrivate")), hasVisibility(Visibility.PRIVATE));
    }

    @Test public void canMatchPublicMethodVisibility() throws Exception {
        assertThat(object(subject).method(hasName("getFieldPublic")), hasVisibility(Visibility.PUBLIC));
    }

    @Test public void canMatchPackageMethodVisibility() throws Exception {
        assertThat(object(subject).method(hasName("getFieldPackage")), hasVisibility(Visibility.DEFAULT));
    }

    @Test public void canMatchProtectedMethodVisibility() throws Exception {
        assertThat(object(subject).method(hasName("getFieldProtected")), hasVisibility(Visibility.PROTECTED));
    }

    @Test public void canMatchPrivateMethodVisibility() throws Exception {
        assertThat(object(subject).method(hasName("getFieldPrivate")), hasVisibility(Visibility.PRIVATE));
    }
}
