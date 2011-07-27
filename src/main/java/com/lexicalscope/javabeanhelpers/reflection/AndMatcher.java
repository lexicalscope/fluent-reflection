package com.lexicalscope.javabeanhelpers.reflection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Inspired by LambdaJ
 * 
 * @author tim
 * 
 * @param <T>
 */
final class AndMatcher<T> extends ReflectionMatcher<T> {
	private final Matcher<T>[] matchers;

	private AndMatcher(final Matcher<T>... matchers) {
		this.matchers = matchers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matchesSafely(final T item) {
		for (final Matcher<T> matcher : matchers) {
			if (!matcher.matches(item)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void describeTo(final Description description) {
		for (int i = 0; i < matchers.length; i++) {
			description.appendDescriptionOf(matchers[i]);
			if (i + 1 < matchers.length) {
				description.appendText(" and ");
			}
		}
	}

	/**
	 * Creates an and matcher combining all the passed matchers
	 * 
	 * @param matchers
	 *            The matchers to be put in and
	 * @return A matcher that return true if all of the matchers return true
	 */
	@Factory
	public static <T> AndMatcher<T> and(final Matcher<T>... matchers) {
		return new AndMatcher<T>(matchers);
	}
}