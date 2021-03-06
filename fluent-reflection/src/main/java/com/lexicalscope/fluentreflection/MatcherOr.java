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

import java.util.List;

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
final class MatcherOr<T> extends ReflectionMatcher<T> {
    private final List<? extends Matcher<? super T>> matchers;

    private MatcherOr(final List<? extends Matcher<? super T>> matchers) {
        this.matchers = matchers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matchesSafely(final T item) {
        for (final Matcher<? super T> matcher : matchers) {
            if (matcher.matches(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeTo(final Description description) {
        for (int i = 0; i < matchers.size(); i++) {
            description.appendDescriptionOf(matchers.get(i));
            if (i + 1 < matchers.size()) {
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
    public static <T> MatcherOr<T> orOf(final List<? extends Matcher<? super T>> matchers) {
        return new MatcherOr<T>(matchers);
    }
}