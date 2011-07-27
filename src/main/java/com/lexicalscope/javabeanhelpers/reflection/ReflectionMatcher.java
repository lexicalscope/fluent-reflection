package com.lexicalscope.javabeanhelpers.reflection;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public abstract class ReflectionMatcher<T> extends TypeSafeMatcher<T> {
	/**
	 * Creates an and matcher combining this matcher and the given one
	 * 
	 * @param matcher
	 *            The matcher to be put in and with this one
	 * @return A matcher that return true if this matcher and the passed one
	 *         return true
	 */
	@SuppressWarnings("unchecked")
	public ReflectionMatcher<T> and(final Matcher<T> matcher) {
		return AndMatcher.and(this, matcher);
	}

	/**
	 * Creates an or matcher combining this matcher and the given one
	 * 
	 * @param matcher
	 *            The matcher to be put in or with this one
	 * @return A matcher that return true if this matcher or the passed one
	 *         return true
	 */
	@SuppressWarnings("unchecked")
	public ReflectionMatcher<T> or(final Matcher<T> matcher) {
		return OrMatcher.or(this, matcher);
	}
}
