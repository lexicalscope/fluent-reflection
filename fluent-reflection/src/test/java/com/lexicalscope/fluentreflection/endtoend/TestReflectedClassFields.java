package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.FluentReflection.*;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.lexicalscope.fluentreflection.ReflectedField;
import com.lexicalscope.fluentreflection.ReflectedObject;


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
    public static class Fields {
        public String field0;
        String field1;
        protected String field2;
        private String field3;
        public static String staticField0;
    }

    @Test public void canFindFieldByName() throws SecurityException, NoSuchFieldException {
        assertThat(
                type(Fields.class).fields(hasName("field0")),
                contains(reflectingOnField(Fields.class.getDeclaredField("field0"))));
    }

    @Test public void canNotFindStaticFieldOfBoundObject() throws SecurityException, NoSuchFieldException {
        assertThat(
                object(new Fields()).fields(hasName("staticField0")),
                Matchers.<ReflectedField>empty());
    }

    @Test public void canWriteField() throws SecurityException, NoSuchFieldException {
        final ReflectedObject<Fields> object = object(new Fields());
        final ReflectedField field = object.field(hasName("field0"));

        field.call("value");
        assertThat(
                field.call(),
                equalTo((Object) "value"));
    }
}
