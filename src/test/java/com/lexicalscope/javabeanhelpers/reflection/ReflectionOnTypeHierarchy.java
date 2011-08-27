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
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class ReflectionOnTypeHierarchy {
	@Test
	public void topLevelInterface() {
		MatcherAssert.assertThat(type(ExampleInterface.class), hasNoInterfaces());
	}

	@Test
	public void ancestorInterfacesAreFound() {
		assertThat(type(ExampleSuperclass.class), hasInterface(ExampleSuperinterface.class));
	}
}
