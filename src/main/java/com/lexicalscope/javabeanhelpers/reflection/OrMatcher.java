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
final class OrMatcher<T> extends ReflectionMatcher<T> {
	private final Matcher<T>[] matchers;

	private OrMatcher(final Matcher<T>... matchers) {
		this.matchers = matchers;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean matchesSafely(final T item) {
		for (final Matcher<T> matcher : matchers) {
			if (matcher.matches(item)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void describeTo(final Description description) {
		for (int i = 0; i < matchers.length; i++) {
			description.appendDescriptionOf(matchers[i]);
			if (i + 1 < matchers.length) {
				description.appendText(" or ");
			}
		}
	}

	/**
	 * Creates an or matcher combining all the passed matchers
	 * 
	 * @param matchers
	 *            The matchers to be put in or
	 * @return A matcher that return true if at least one of the matchers return
	 *         true
	 */
	@Factory
	public static <T> OrMatcher<T> or(final Matcher<T>... matchers) {
		return new OrMatcher<T>(matchers);
	}
}