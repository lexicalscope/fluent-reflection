package com.lexicalscope.javabeanhelpers.reflection;

import static com.lexicalscope.javabeanhelpers.reflection.MethodMatchers.*;
import static com.lexicalscope.javabeanhelpers.reflection.Reflect.type;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class ReflectionOnInterfaceTest {
	@Test
	public void classUnderReflectionReturnsClassUnderReflection() {
		assertThat(type(ExampleInterface.class).getClassUnderReflection(), equalTo(ExampleInterface.class));
	}

	@Test
	public void methodsCanBeSelectedByPrefix() {
		assertThat(
				type(ExampleInterface.class).methods(startingWith("get")),
				hasItem(named("getPropertyOne")));
	}

	@Test
	public void methodsCanBeSelectedBySuffix() {
		assertThat(
				type(ExampleInterface.class).methods(endingWith("One")),
				hasItem(named("getPropertyOne")));
	}

	@Test
	public void methodsCanBeSelectedByRegularExpression() {
		assertThat(
				type(ExampleInterface.class).methods(matching(".*Property.*")),
				hasItem(named("getPropertyOne")));
	}

	@Test
	public void methodsWithNoArgumentsCanBeSelected() {
		assertThat(
				type(ExampleInterface.class).methods(withArguments()),
				hasItem(named("getPropertyOne")));
	}

	@Test
	public void methodCanBeSelectedByArgument() {
		assertThat(
				type(ExampleInterface.class).methods(withArguments(String.class)),
				hasItem(named("setPropertyOne")));
	}
}
