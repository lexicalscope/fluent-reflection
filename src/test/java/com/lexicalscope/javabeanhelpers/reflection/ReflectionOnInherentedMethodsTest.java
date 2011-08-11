package com.lexicalscope.javabeanhelpers.reflection;

import static com.lexicalscope.javabeanhelpers.reflection.MethodMatchers.*;
import static com.lexicalscope.javabeanhelpers.reflection.Reflect.type;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

import org.junit.Test;

public class ReflectionOnInherentedMethodsTest {
	@Test
	public void allMethodsIncludeSuperclassMethods() {
		assertThat(
				type(ExampleSubinterface.class).methods(contains("Superclass")),
				hasItem(named("getSuperclassProperty")));
	}

	@Test
	public void allMethodsIncludeSubclassMethods() {
		assertThat(
				type(ExampleSubinterface.class).methods(contains("Subclass")),
				hasItem(named("getSubclassProperty")));
	}
}
