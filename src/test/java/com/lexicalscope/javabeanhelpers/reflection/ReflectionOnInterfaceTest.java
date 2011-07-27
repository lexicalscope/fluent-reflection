package com.lexicalscope.javabeanhelpers.reflection;

import static com.lexicalscope.javabeanhelpers.reflection.MethodMatchers.named;
import static com.lexicalscope.javabeanhelpers.reflection.MethodMatchers.startingWith;
import static com.lexicalscope.javabeanhelpers.reflection.MethodMatchers.withArguments;
import static com.lexicalscope.javabeanhelpers.reflection.Reflect.type;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

/**
 * Unit test for simple App.
 */
public class ReflectionOnInterfaceTest {
	@org.junit.Test
	public void classUnderReflectionIsAvailable() {
		assertThat(type(ExampleInterface.class).getClassUnderReflection(), equalTo(ExampleInterface.class));
	}

	@org.junit.Test
	public void getMethodsCanBeSelected() {
		assertThat(
				type(ExampleInterface.class).methods(startingWith("get")),
				hasItem(named("getPropertyOne")));
	}

	@org.junit.Test
	public void methodsWithNoArgumentsCanBeSelected() {
		assertThat(
				type(ExampleInterface.class).methods(withArguments()),
				hasItem(named("getPropertyOne")));
	}

	@org.junit.Test
	public void getMethodsWithNoArgumentsCanBeSelected() {
		assertThat(
				type(ExampleInterface.class).methods(withArguments(String.class)),
				hasItem(named("setPropertyOne")));
	}
}
