package com.lexicalscope.javabeanhelpers.reflection;

import static ch.lambdaj.Lambda.convert;
import static org.hamcrest.Matchers.arrayContaining;

import java.util.List;

import org.hamcrest.Description;

public class MethodMatchers {
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
}
