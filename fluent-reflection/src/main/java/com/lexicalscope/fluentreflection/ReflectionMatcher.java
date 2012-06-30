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

import static com.lexicalscope.fluentreflection.MatcherAnd.andOf;
import static com.lexicalscope.fluentreflection.MatcherOr.orOf;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Base class for all the matchers included in the library.
 *
 * @author tim
 *
 * @param <T>
 */
public abstract class ReflectionMatcher<T> extends TypeSafeMatcher<T> {
    /**
     * Creates an "and" matcher combining this matcher and the given one
     *
     * @param matcher
     *            The matcher to be put in and with this one
     * @return A matcher that return true if this matcher and the passed one
     *         return true
     */
    public ReflectionMatcher<T> and(final Matcher<? super T> matcher) {
        final List<Matcher<? super T>> list = new ArrayList<Matcher<? super T>>();
        list.add(this);
        list.add(matcher);
        return andOf(list);
    }

    /**
     * Creates an "or" matcher combining this matcher and the given one
     *
     * @param matcher
     *            The matcher to be put in or with this one
     * @return A matcher that return true if this matcher or the passed one
     *         return true
     */
    public ReflectionMatcher<T> or(final Matcher<? super T> matcher) {
        final List<Matcher<? super T>> list = new ArrayList<Matcher<? super T>>();
        list.add(this);
        list.add(matcher);
        return orOf(list);
    }

    static <T> Matcher<T> allOf(
            final Matcher<? super T> T0,
            final Matcher<? super T> T1) {
        final List<Matcher<? super T>> list = new ArrayList<Matcher<? super T>>();
        list.add(T0);
        list.add(T1);
        return andOf(list);
    }
}
