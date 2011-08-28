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

import static ch.lambdaj.Lambda.*;
import static org.hamcrest.Matchers.arrayContaining;

import java.util.List;
import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class ReflectionMatchers {
	public static ReflectionMatcher<ReflectedMethod> startingWith(final String prefix) {
		return new ReflectionMatcher<ReflectedMethod>() {
			@Override
			public boolean matchesSafely(final ReflectedMethod arg) {
				return arg.getName().startsWith(prefix);
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("method starting with ").appendValue(prefix);
			}
		};
	}

	public static ReflectionMatcher<ReflectedMethod> endingWith(final String suffix) {
		return new ReflectionMatcher<ReflectedMethod>() {
			@Override
			public boolean matchesSafely(final ReflectedMethod arg) {
				return arg.getName().endsWith(suffix);
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("method ending with ").appendValue(suffix);
			}
		};
	}

	public static ReflectionMatcher<ReflectedMethod> matching(final String regex) {
		final Pattern pattern = Pattern.compile(regex);
		return new ReflectionMatcher<ReflectedMethod>() {
			@Override
			public boolean matchesSafely(final ReflectedMethod arg) {
				return pattern.matcher(arg.getName()).matches();
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("method matching ").appendValue(regex);
			}
		};
	}

	public static ReflectionMatcher<ReflectedMethod> contains(final CharSequence substring) {
		return new ReflectionMatcher<ReflectedMethod>() {
			@Override
			public boolean matchesSafely(final ReflectedMethod arg) {
				return arg.getName().contains(substring);
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("method containing ").appendValue(substring);
			}
		};
	}

	public static ReflectionMatcher<ReflectedMethod> named(final String name) {
		return new ReflectionMatcher<ReflectedMethod>() {
			@Override
			public boolean matchesSafely(final ReflectedMethod arg) {
				return arg.getName().startsWith(name);
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("method named ").appendValue(name);
			}
		};
	}

	public static ReflectionMatcher<ReflectedMethod> withArguments(final Class<?>... expectedArgumentTypes) {
		return new ReflectionMatcher<ReflectedMethod>() {
			@Override
			public boolean matchesSafely(final ReflectedMethod arg) {
				final List<Class<?>> actualArgumentTypes =
						convert(arg.getArgumentTypes(), new ReflectedType2ClassConvertor());

				if (expectedArgumentTypes == null || expectedArgumentTypes.length == 0) {
					return actualArgumentTypes.size() == 0;
				}
				if (expectedArgumentTypes.length != actualArgumentTypes.size()) {
					return false;
				}
				return arrayContaining(actualArgumentTypes.toArray(new Class[actualArgumentTypes.size()])).matches(
						expectedArgumentTypes);
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("method with arguments ").appendValue(expectedArgumentTypes);
			}
		};
	}

	public static ReflectionMatcher<ReflectedMethod> declaredBy(final Class<?> declaringKlass) {
		return new ReflectionMatcher<ReflectedMethod>() {
			@Override
			public boolean matchesSafely(final ReflectedMethod arg) {
				return arg.getDeclaringClass().getClassUnderReflection().equals(declaringKlass);
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("method declared by ").appendValue(declaringKlass);
			}
		};
	}

	public static Matcher<ReflectedType<?>> hasNoInterfaces() {
		return new ReflectionMatcher<ReflectedType<?>>() {
			@Override
			public boolean matchesSafely(final ReflectedType<?> arg) {
				return arg.getInterfaces().isEmpty();
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("type that implements no interfaces");
			}
		};
	}

	public static Matcher<ReflectedType<?>> hasInterface(final Class<?> interfac3) {
		return new ReflectionMatcher<ReflectedType<?>>() {
			@Override
			public boolean matchesSafely(final ReflectedType<?> arg) {
				return select(
						arg.getInterfaces(),
						reflectingOn(interfac3)).isEmpty();
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("type that implements no interfaces");
			}
		};
	}

	public static Matcher<ReflectedType<?>> isInterface() {
		return new ReflectionMatcher<ReflectedType<?>>() {
			@Override
			public boolean matchesSafely(final ReflectedType<?> arg) {
				return arg.isInterface();
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("type that is an interface");
			}
		};
	}

	public static Matcher<ReflectedType<?>> reflectingOn(final Class<?> klass) {
		return new ReflectionMatcher<ReflectedType<?>>() {
			@Override
			public boolean matchesSafely(final ReflectedType<?> arg) {
				return arg.getClassUnderReflection().equals(klass);
			}

			@Override
			public void describeTo(final Description description) {
				description.appendText("reflecting on type ").appendValue(klass);
			}
		};
	}
}
