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

import org.hamcrest.Matchers;
import org.junit.Test;

import com.lexicalscope.fluentreflection.ReflectedMethod;

public class TestReflectionOnInterface {
    @Test public void classUnderReflectionReturnsClassUnderReflection() {
        assertThat(
                type(ExampleInterface.class).classUnderReflection(),
                equalTo(ExampleInterface.class));
    }

    @Test public void methodsCanBeSelectedByPrefix() {
        assertThat(
                type(ExampleInterface.class).methods(hasNameStartingWith("get")),
                Matchers.<ReflectedMethod>hasItem(hasName("getPropertyOne")));
    }

    @Test public void methodsCanBeSelectedBySuffix() {
        assertThat(
                type(ExampleInterface.class).methods(hasNameEndingWith("One")),
                Matchers.<ReflectedMethod>hasItem(hasName("getPropertyOne")));
    }

    @Test public void methodsCanBeSelectedByRegularExpression() {
        assertThat(
                type(ExampleInterface.class).methods(hasNameMatching(".*Property.*")),
                Matchers.<ReflectedMethod>hasItem(hasName("getPropertyOne")));
    }

    @Test public void methodsWithNoArgumentsCanBeSelected() {
        assertThat(
                type(ExampleInterface.class).methods(
                        hasArguments()),
                Matchers.<ReflectedMethod>hasItem(hasName("getPropertyOne")));
    }

    @Test public void methodCanBeSelectedByArgument() {
        assertThat(
                type(ExampleInterface.class).methods(hasArguments(String.class)),
                Matchers.<ReflectedMethod>hasItem(hasName("setPropertyOne")));
    }
}
