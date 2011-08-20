package com.lexicalscope.javabeanhelpers.reflection;

import static com.lexicalscope.javabeanhelpers.reflection.ReflectionMatchers.hasNoInterfaces;
import static com.lexicalscope.javabeanhelpers.reflection.Reflect.type;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class ReflectionOnTypeHierarchy {
	@Test
	public void topLevelInterface() {
		assertThat(type(ExampleInterface.class), hasNoInterfaces());
	}
}
