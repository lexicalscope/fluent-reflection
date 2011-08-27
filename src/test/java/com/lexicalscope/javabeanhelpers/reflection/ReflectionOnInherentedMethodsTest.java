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

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class ReflectionOnInherentedMethodsTest {
	@Test
	public void subinterfaceMethodsIncludeSuperinterfaceMethods() {
		MatcherAssert.assertThat(
				type(ExampleSubinterface.class).methods(contains("Superinterface")),
				hasItem(named("getSuperinterfaceProperty")));
	}

	@Test
	public void subinterfaceMethodsIncludeSubinterfaceMethods() {
		MatcherAssert.assertThat(
				type(ExampleSubinterface.class).methods(contains("Subinterface")),
				hasItem(named("getSubinterfaceProperty")));
	}

	@Test
	public void subclassMethodsIncludeSuperinterfaceMethods() {
		MatcherAssert.assertThat(
				type(ExampleSubclass.class).methods(contains("Superinterface")),
				hasItem(named("getSuperinterfaceProperty")));
	}

	@Test
	public void subclassMethodsIncludeSubinterfaceMethods() {
		MatcherAssert.assertThat(
				type(ExampleSubclass.class).methods(contains("Subinterface")),
				hasItem(named("getSubinterfaceProperty")));
	}

	@Test
	public void subclassMethodsIncludeSuperclassMethods() {
		MatcherAssert.assertThat(
				type(ExampleSubclass.class).methods(contains("Superclass")),
				hasItem(named("getSuperclassProperty")));
	}

	@Test
	public void subclassMethodsIncludeSubclassMethods() {
		MatcherAssert.assertThat(
				type(ExampleSubclass.class).methods(contains("Subclass")),
				hasItem(named("getSubclassProperty")));
	}

	@Test
	public void subsubclassMethodsIncludeSuperclassMethods() {
		MatcherAssert.assertThat(
				type(ExampleSubsubclass.class).methods(contains("Superclass")),
				hasItem(named("getSuperclassProperty")));
	}

	@Test
	public void declaredSubclassMethodsAreFound() {
		final List<ReflectedMethod> methodsDeclaredByExampleSubclass =
				type(ExampleSubclass.class).methods(declaredBy(ExampleSubclass.class));

		MatcherAssert.assertThat(
				methodsDeclaredByExampleSubclass,
				hasItem(named("getSubclassProperty")));

		MatcherAssert.assertThat(
				methodsDeclaredByExampleSubclass.size(),
				equalTo(1));
	}

	@Test
	public void declaredSuperclassMethodsAreFound() {
		final List<ReflectedMethod> methodsDeclaredByExampleSuperclass =
				type(ExampleSubclass.class).methods(declaredBy(ExampleSuperclass.class));

		MatcherAssert.assertThat(
				methodsDeclaredByExampleSuperclass,
				hasItem(named("getSuperclassProperty")));

		MatcherAssert.assertThat(
				methodsDeclaredByExampleSuperclass,
				hasItem(named("getSubinterfaceProperty")));

		MatcherAssert.assertThat(
				methodsDeclaredByExampleSuperclass,
				hasItem(named("getSuperinterfaceProperty")));

		MatcherAssert.assertThat(
				methodsDeclaredByExampleSuperclass.size(),
				equalTo(3));
	}
}
