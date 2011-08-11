package com.lexicalscope.javabeanhelpers.reflection;

import static com.lexicalscope.javabeanhelpers.reflection.MethodMatchers.*;
import static com.lexicalscope.javabeanhelpers.reflection.Reflect.type;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Test;

public class ReflectionOnInherentedMethodsTest {
	@Test
	public void subinterfaceMethodsIncludeSuperinterfaceMethods() {
		assertThat(
				type(ExampleSubinterface.class).methods(contains("Superinterface")),
				hasItem(named("getSuperinterfaceProperty")));
	}

	@Test
	public void subinterfaceMethodsIncludeSubinterfaceMethods() {
		assertThat(
				type(ExampleSubinterface.class).methods(contains("Subinterface")),
				hasItem(named("getSubinterfaceProperty")));
	}

	@Test
	public void subclassMethodsIncludeSuperinterfaceMethods() {
		assertThat(
				type(ExampleSubclass.class).methods(contains("Superinterface")),
				hasItem(named("getSuperinterfaceProperty")));
	}

	@Test
	public void subclassMethodsIncludeSubinterfaceMethods() {
		assertThat(
				type(ExampleSubclass.class).methods(contains("Subinterface")),
				hasItem(named("getSubinterfaceProperty")));
	}

	@Test
	public void subclassMethodsIncludeSuperclassMethods() {
		assertThat(
				type(ExampleSubclass.class).methods(contains("Superclass")),
				hasItem(named("getSuperclassProperty")));
	}

	@Test
	public void subclassMethodsIncludeSubclassMethods() {
		assertThat(
				type(ExampleSubclass.class).methods(contains("Subclass")),
				hasItem(named("getSubclassProperty")));
	}

	@Test
	public void declaredSubclassMethodsAreFound() {
		final List<ReflectedMethod> methodsDeclaredByExampleSubclass =
				type(ExampleSubclass.class).methods(declaredBy(ExampleSubclass.class));

		assertThat(
				methodsDeclaredByExampleSubclass,
				hasItem(named("getSubclassProperty")));

		assertThat(
				methodsDeclaredByExampleSubclass.size(),
				equalTo(1));
	}

	@Test
	public void declaredSuperclassMethodsAreFound() {
		final List<ReflectedMethod> methodsDeclaredByExampleSuperclass =
				type(ExampleSubclass.class).methods(declaredBy(ExampleSuperclass.class));

		assertThat(
				methodsDeclaredByExampleSuperclass,
				hasItem(named("getSuperclassProperty")));

		assertThat(
				methodsDeclaredByExampleSuperclass,
				hasItem(named("getSubinterfaceProperty")));

		assertThat(
				methodsDeclaredByExampleSuperclass,
				hasItem(named("getSuperinterfaceProperty")));

		assertThat(
				methodsDeclaredByExampleSuperclass.size(),
				equalTo(3));
	}
}
