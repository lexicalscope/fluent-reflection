package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.FluentReflection.*;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lexicalscope.fluentreflection.FieldNotFoundException;
import com.lexicalscope.fluentreflection.FluentClass;
import com.lexicalscope.fluentreflection.FluentField;
import com.lexicalscope.fluentreflection.FluentObject;
import com.lexicalscope.fluentreflection.ReflectionRuntimeException;


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

public class TestReflectedClassFields {
    @Rule public ExpectedException exception = ExpectedException.none();

    public static class Fields {
        public String publicField;
        String packageField;
        protected String field2;
        private String field3;
        public static String staticField0;
        public static final String staticFinalField0 = "value";
        public final String finalField0 = "value";
    }

    @Test public void canFindFieldByName() throws SecurityException, NoSuchFieldException {
        assertThat(
                type(Fields.class).fields(hasName("publicField")),
                contains(isReflectingOnField(Fields.class.getDeclaredField("publicField"))));
    }

    @Test public void canNotFindStaticFieldOfBoundObject() throws SecurityException, NoSuchFieldException {
        assertThat(
                object(new Fields()).fields(hasName("staticField0")),
                Matchers.<FluentField>empty());
    }

    @Test public void canWriteField() throws SecurityException, NoSuchFieldException {
        final FluentObject<Fields> object = object(new Fields());
        final FluentField field = object.field(hasName("publicField"));

        field.call("value");
        assertThat(
                field.call().value(),
                equalTo((Object) "value"));
    }

    @Test public void fieldDeclaringTypeIsCorrect() throws SecurityException, NoSuchFieldException {
        assertThat(
                type(Fields.class).field(hasName("publicField")),
                declaredBy(Fields.class));
    }

    @Test public void finalFieldsAreMarkedAsFinal() throws SecurityException, NoSuchFieldException {
        assertThat(
                type(Fields.class).field(hasName("finalField0")),
                isFinal());
    }

    @Test public void propertyNameIsAvailable() throws SecurityException, NoSuchFieldException {
        assertThat(
                type(Fields.class).field(hasName("publicField")),
                hasName("publicField").and(hasPropertyName("publicField")));
    }

    @Test public void fieldHasZeroArgument() throws SecurityException, NoSuchFieldException {
        assertThat(
                type(Fields.class).field(hasName("publicField")),
                hasArgumentCount(0));
    }

    @Test public void publicFieldToStringIsUseful() throws SecurityException, NoSuchFieldException {
        assertThat(
                type(Fields.class).field(hasName("publicField")),
                hasToString("public java.lang.String publicField"));
    }

    @Test public void packageToStringIsUseful() throws SecurityException, NoSuchFieldException {
        assertThat(
                type(Fields.class).field(hasName("packageField")),
                hasToString("java.lang.String packageField"));
    }

    @Test public void staticToStringIsUseful() throws SecurityException, NoSuchFieldException {
        assertThat(
                type(Fields.class).field(hasName("staticField0")),
                hasToString("public static java.lang.String staticField0"));
    }

    @Test public void staticFinalToStringIsUseful() throws SecurityException, NoSuchFieldException {
        assertThat(
                type(Fields.class).field(hasName("staticFinalField0")),
                hasToString("public static final java.lang.String staticFinalField0"));
    }

    @Test public void boundToStringIsUseful() throws SecurityException, NoSuchFieldException {
        assertThat(
                object(new Fields()).field(hasName("publicField")),
                hasToString(startsWith("public java.lang.String publicField")));
    }

    @Test public void boundEqualsIsTrueForTheSameInstance() throws SecurityException, NoSuchFieldException {
        final Fields instance = new Fields();
        assertThat(
                object(instance).field(hasName("publicField")),
                equalTo(object(instance).field(hasName("publicField"))));
    }

    @Test public void hashCodeIsTheSameForTheSameBoundInstance() throws SecurityException, NoSuchFieldException {
        final Fields instance = new Fields();
        assertThat(
                object(instance).field(hasName("publicField")).hashCode(),
                equalTo(object(instance).field(hasName("publicField")).hashCode()));
    }

    @Test public void boundEqualsIsFalseForTheDifferentBoundInstances() throws SecurityException, NoSuchFieldException {
        assertThat(
                object(new Fields()).field(hasName("publicField")),
                not(equalTo(object(new Fields()).field(hasName("publicField")))));
    }

    @Test public void equalsIsTrueWhenTheFieldsAreTheSameAsEachOther() throws SecurityException, NoSuchFieldException {
        final FluentField field = object(new Fields()).field(hasName("publicField"));
        assertThat(field, equalTo(field));
    }

    @Test public void equalsIsTrueWhenTheThatIsARandomOtherObject() throws SecurityException, NoSuchFieldException {
        final FluentField field = object(new Fields()).field(hasName("publicField"));
        assertThat(field, not(equalTo(new Object())));
    }

    @Test public void cannotReadFieldWithoutInstance() throws SecurityException, NoSuchFieldException {
        final FluentClass<Fields> object = type(Fields.class);
        final FluentField field = object.field(hasName("publicField"));

        exception.expect(ReflectionRuntimeException.class);
        exception.expectMessage("reading a field requires an instance argument");
        field.call();
    }

    @Test public void cannotCallFieldWithTooManyArguments() throws SecurityException, NoSuchFieldException {
        final FluentClass<Fields> object = type(Fields.class);
        final FluentField field = object.field(hasName("publicField"));

        exception.expect(ReflectionRuntimeException.class);
        exception.expectMessage("reading a field requires one argument, writing a field requires two arguments. Got 3 arguments");
        field.call(new Fields(), "value", "excess argument");
    }

    @Test public void missingFieldThrowsException() throws SecurityException, NoSuchFieldException {
        exception.expect(FieldNotFoundException.class);
        object(new Fields()).field(hasName("noSuchField"));
    }
}