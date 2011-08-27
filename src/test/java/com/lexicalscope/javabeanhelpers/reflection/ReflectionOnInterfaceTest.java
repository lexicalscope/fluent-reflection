package com.lexicalscope.javabeanhelpers.reflection;

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

import static com.lexicalscope.javabeanhelpers.reflection.Reflect.type;
import static com.lexicalscope.javabeanhelpers.reflection.ReflectionMatchers.*;
import static org.hamcrest.Matchers.*;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class ReflectionOnInterfaceTest {
	@Test
	public void classUnderReflectionReturnsClassUnderReflection() {
		MatcherAssert.assertThat(
				type(ExampleInterface.class).getClassUnderReflection(),
				equalTo(ExampleInterface.class));
	}

	@Test
	public void methodsCanBeSelectedByPrefix() {
		MatcherAssert.assertThat(
				type(ExampleInterface.class).methods(startingWith("get")),
				hasItem(named("getPropertyOne")));
	}

	@Test
	public void methodsCanBeSelectedBySuffix() {
		MatcherAssert.assertThat(
				type(ExampleInterface.class).methods(endingWith("One")),
				hasItem(named("getPropertyOne")));
	}

	@Test
	public void methodsCanBeSelectedByRegularExpression() {
		MatcherAssert.assertThat(
				type(ExampleInterface.class).methods(matching(".*Property.*")),
				hasItem(named("getPropertyOne")));
	}

	@Test
	public void methodsWithNoArgumentsCanBeSelected() {
		MatcherAssert.assertThat(
				type(ExampleInterface.class).methods(withArguments()),
				hasItem(named("getPropertyOne")));
	}

	@Test
	public void methodCanBeSelectedByArgument() {
		MatcherAssert.assertThat(
				type(ExampleInterface.class).methods(withArguments(String.class)),
				hasItem(named("setPropertyOne")));
	}
}
