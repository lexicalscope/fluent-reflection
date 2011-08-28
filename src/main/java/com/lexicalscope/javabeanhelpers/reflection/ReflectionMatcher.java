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

import static com.lexicalscope.javabeanhelpers.reflection.AndMatcher.andOf;
import static com.lexicalscope.javabeanhelpers.reflection.ListBuilder.list;

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
	public ReflectionMatcher<T> and(final Matcher<T> matcher) {
		return andOf(list((Matcher<T>) this).add(matcher).$());
	}

	/**
	 * Creates an or matcher combining this matcher and the given one
	 * 
	 * @param matcher
	 *            The matcher to be put in or with this one
	 * @return A matcher that return true if this matcher or the passed one
	 *         return true
	 */
	public ReflectionMatcher<T> or(final Matcher<T> matcher) {
		return OrMatcher.orOf(list((Matcher<T>) this).add(matcher).$());
	}
}
