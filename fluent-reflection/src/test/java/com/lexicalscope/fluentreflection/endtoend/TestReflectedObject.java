package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.FluentReflection.object;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.lexicalscope.fluentreflection.FluentMethod;
import com.lexicalscope.fluentreflection.FluentObject;

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

public class TestReflectedObject {
    static class ExampleObject {
        int method() {
            return 42;
        }

        int doubleIt(final int arg) {
            return arg * 2;
        }

        static void staticMethod() {

        }
    }

    private final FluentObject<ExampleObject> reflectedInstance = object(new ExampleObject());

    @Test public void instanceMethodsCanBeCalled() throws Exception {
        assertThat((Integer) reflectedInstance.method("method").call().value(), equalTo(42));
    }

    @Test public void instanceMethodsCanHaveReturnTypeBoundAndBeCalled() throws Exception {
        assertThat(reflectedInstance.method("method").as(Integer.class).call().value(), equalTo(42));
    }

    @Test public void instanceMethodsWithArgumentsCanBeCalled() throws Exception {
        assertThat((Integer) reflectedInstance.method("doubleIt").call(42).value(), equalTo(84));
    }

    @Test public void instanceMethodArgumentsCountIsCorrect() throws Exception {
        assertThat(reflectedInstance.method("doubleIt").argCount(), equalTo(1));
    }

    @Test public void instanceMethodArgumentTypeIsCorrect() throws Exception {
        assertThat(
                reflectedInstance.method("doubleIt").args(),
                contains(reflectingOn(int.class)));
    }

    @Test public void instanceMethodDeclaringTypeIsCorrect() throws Exception {
        assertThat(
                reflectedInstance.method("doubleIt").declarer(),
                reflectingOn(ExampleObject.class));
    }

    @Test public void staticMethodsAreNotFound() throws Exception {
        assertThat(reflectedInstance.methods(), not(Matchers.<FluentMethod>hasItem(isStatic())));
    }

    @Test public void methodsAreFound() throws Exception {
        assertThat(reflectedInstance.methods(), Matchers.<FluentMethod>hasItem(hasName("method")));
        assertThat(reflectedInstance.methods(), Matchers.<FluentMethod>hasItem(hasName("doubleIt")));
    }

    @Test public void classUnderReflectionIsCorrect() throws Exception {
        assertThat(reflectedInstance.classUnderReflection(), equalTo(ExampleObject.class));
        assertThat(reflectedInstance.reflectedClass().classUnderReflection(), equalTo(ExampleObject.class));
    }
}
