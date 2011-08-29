package com.lexicalscope.fluentreflection;

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

import static ch.lambdaj.Lambda.select;
import static org.hamcrest.Matchers.anything;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.matchers.ReflectionMatchers;

/**
 * Not thread safe
 * 
 * @author tim
 * 
 * @param <T>
 */
class ReflectedTypeImpl<T> implements ReflectedType<T> {
	private final Class<T> klass;

	private List<ReflectedMethod> reflectedMethods;
	private List<ReflectedType<?>> interfacesAndSuperClass;

	public ReflectedTypeImpl(final Class<T> klass) {
		this.klass = klass;
	}

	private List<ReflectedMethod> reflectedMethods() {
		if (reflectedMethods == null) {
			final List<ReflectedMethod> result = new ArrayList<ReflectedMethod>();

			for (final ReflectedType<?> klassToReflect : interfacesAndSuperClasses()) {
				result.addAll(getDeclaredMethodsOfClass(klassToReflect.getClassUnderReflection()));
			}

			reflectedMethods = result;
		}
		return reflectedMethods;
	}

	private List<ReflectedType<?>> interfacesAndSuperClasses() {
		if (interfacesAndSuperClass == null) {
			interfacesAndSuperClass = new TypeHierarchyCalculation().interfacesAndSuperClass(klass);
		}
		return interfacesAndSuperClass;
	}

	private List<ReflectedMethod> getDeclaredMethodsOfClass(final Class<?> klassToReflect) {
		final List<ReflectedMethod> result = new ArrayList<ReflectedMethod>();
		final Method[] declaredMethods = klassToReflect.getDeclaredMethods();
		for (final Method method : declaredMethods) {
			result.add(new ReflectedMethodImpl(method));
		}
		return result;
	}

	@Override
	public Class<T> getClassUnderReflection() {
		return klass;
	}

	@Override
	public List<ReflectedMethod> methods(final Matcher<? super ReflectedMethod> methodMatcher) {
		return select(reflectedMethods(), methodMatcher);
	}

	@Override
	public List<ReflectedMethod> methods() {
		return methods(anything());
	}

	static <T> ReflectedTypeImpl<?> create(final Class<T> from) {
		return new ReflectedTypeImpl<T>(from);
	}

	@Override
	public List<ReflectedType<?>> getInterfaces() {
		return select(interfacesAndSuperClasses(), ReflectionMatchers.typeIsInterface());
	}

	@Override
	public boolean isInterface() {
		return false;
	}
}
