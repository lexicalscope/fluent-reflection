package com.lexicalscope.javabeanhelpers.reflection;

import static ch.lambdaj.Lambda.convert;
import static org.hamcrest.Matchers.arrayContaining;

import java.util.List;
import java.util.regex.Pattern;

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
}
