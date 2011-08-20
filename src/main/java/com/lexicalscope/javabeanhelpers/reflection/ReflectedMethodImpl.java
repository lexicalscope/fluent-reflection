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

import java.lang.reflect.Method;
import java.util.List;

import ch.lambdaj.Lambda;

class ReflectedMethodImpl implements ReflectedMethod {
	private final Method method;

	public ReflectedMethodImpl(final Method method) {
		this.method = method;
	}

	@Override
	public String getName() {
		return method.getName();
	}

	@Override
	public List<ReflectedType<?>> getArgumentTypes() {
		return Lambda.convert(method.getParameterTypes(), new Class2ReflectedTypeConvertor());
	}

	@Override
	public ReflectedType<?> getDeclaringClass() {
		return ReflectedTypeImpl.create(method.getDeclaringClass());
	}

	@Override
	public String toString() {
		return method.toString();
	}
}
