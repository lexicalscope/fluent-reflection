package com.lexicalscope.fluentreflection.endtoend;

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

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;

import org.junit.Test;

import com.lexicalscope.fluentreflection.FluentClass;

public class TestReflectionOnTypeHierarchy {
    @Test
    public void topLevelClassImplementsNoInterfaces() {
        assertThat(type(ExampleClass.class), hasNoInterfaces());
    }

    @Test
    public void topLevelClassHasNoSuperclasses() {
        assertThat(type(ExampleClass.class), hasNoInterfaces());
    }

    @Test
    public void topLevelInterfaceHasNoInterfaces() {
        assertThat(type(ExampleInterface.class), hasNoInterfaces());
    }

    @Test
    public void ancestorInterfacesAreFound() {
        assertThat(type(ExampleSuperclass.class), hasInterface(ExampleSuperinterface.class));
    }

    @Test
    public void superClassesReturnedInOrder() {
        final List<FluentClass<?>> expectedSuperclasses =
                ListBuilder.<FluentClass<?>>list(type(ExampleSubclass.class)).add(type(ExampleSuperclass.class)).$();

        assertThat(type(ExampleSubsubclass.class).superclasses(), equalTo(expectedSuperclasses));
    }
}
