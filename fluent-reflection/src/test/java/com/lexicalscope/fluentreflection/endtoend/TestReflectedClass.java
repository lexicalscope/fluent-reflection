package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectedTypeReflectingOn;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

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

public class TestReflectedClass {
    @Test public void hashCodeWorks()
    {
        assertThat(type(String.class).hashCode(), equalTo(type(String.class).hashCode()));
    }

    @Test public void equalsWorks()
    {
        assertThat(type(String.class), equalTo(type(String.class)));
        assertThat(type(String.class), not(equalTo(null)));
    }

    @Test public void canGetReflectedClassAsASuperType()
    {
        assertThat(
                type(List.class).asType(reflectedTypeReflectingOn(Iterable.class)).classUnderReflection(),
                equalTo((Object) Iterable.class));
    }
}
