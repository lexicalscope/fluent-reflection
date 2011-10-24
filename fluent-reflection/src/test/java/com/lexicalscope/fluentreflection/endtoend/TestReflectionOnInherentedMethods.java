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

import org.hamcrest.Matchers;
import org.junit.Test;

import com.lexicalscope.fluentreflection.ReflectedMethod;

public class TestReflectionOnInherentedMethods {
	@Test
	public void subinterfaceMethodsIncludeSuperinterfaceMethods() {
		assertThat(
				type(ExampleSubinterface.class).methods(callableHasNameContaining("Superinterface")),
				Matchers.<ReflectedMethod>hasItem(callableHasName("getSuperinterfaceProperty")));
	}

	@Test
	public void subinterfaceMethodsIncludeSubinterfaceMethods() {
		assertThat(
				type(ExampleSubinterface.class).methods(callableHasNameContaining("Subinterface")),
				Matchers.<ReflectedMethod>hasItem(callableHasName("getSubinterfaceProperty")));
	}

	@Test
	public void subclassMethodsIncludeSuperinterfaceMethods() {
		assertThat(
				type(ExampleSubclass.class).methods(callableHasNameContaining("Superinterface")),
				Matchers.<ReflectedMethod>hasItem(callableHasName("getSuperinterfaceProperty")));
	}

	@Test
	public void subclassMethodsIncludeSubinterfaceMethods() {
		assertThat(
				type(ExampleSubclass.class).methods(callableHasNameContaining("Subinterface")),
				Matchers.<ReflectedMethod>hasItem(callableHasName("getSubinterfaceProperty")));
	}

	@Test
	public void subclassMethodsIncludeSuperclassMethods() {
		assertThat(
				type(ExampleSubclass.class).methods(callableHasNameContaining("Superclass")),
				Matchers.<ReflectedMethod>hasItem(callableHasName("getSuperclassProperty")));
	}

	@Test
	public void subclassMethodsIncludeSubclassMethods() {
		assertThat(
				type(ExampleSubclass.class).methods(callableHasNameContaining("Subclass")),
				Matchers.<ReflectedMethod>hasItem(callableHasName("getSubclassProperty")));
	}

	@Test
	public void subsubclassMethodsIncludeSuperclassMethods() {
		assertThat(
				type(ExampleSubsubclass.class).methods(callableHasNameContaining("Superclass")),
				Matchers.<ReflectedMethod>hasItem(callableHasName("getSuperclassProperty")));
	}

	@Test
	public void declaredSubclassMethodsAreFound() {
		final List<ReflectedMethod> methodsDeclaredByExampleSubclass =
				type(ExampleSubclass.class).methods(callableDeclaredBy(ExampleSubclass.class));

		assertThat(
				methodsDeclaredByExampleSubclass,
				Matchers.<ReflectedMethod>hasItem(callableHasName("getSubclassProperty")));

		assertThat(
				methodsDeclaredByExampleSubclass.size(),
				equalTo(1));
	}

	@Test
	public void declaredSuperclassMethodsAreFound() {
		final List<ReflectedMethod> methodsDeclaredByExampleSuperclass =
				type(ExampleSubclass.class).methods(callableDeclaredBy(ExampleSuperclass.class));

		assertThat(
				methodsDeclaredByExampleSuperclass,
				Matchers.<ReflectedMethod>hasItem(callableHasName("getSuperclassProperty")));

		assertThat(
				methodsDeclaredByExampleSuperclass,
				Matchers.<ReflectedMethod>hasItem(callableHasName("getSubinterfaceProperty")));

		assertThat(
				methodsDeclaredByExampleSuperclass,
				Matchers.<ReflectedMethod>hasItem(callableHasName("getSuperinterfaceProperty")));

		assertThat(
				methodsDeclaredByExampleSuperclass.size(),
				equalTo(3));
	}
}
