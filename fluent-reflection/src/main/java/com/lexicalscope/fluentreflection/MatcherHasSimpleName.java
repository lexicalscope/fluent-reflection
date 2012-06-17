package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

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

final class MatcherHasSimpleName extends ReflectionMatcher<ReflectedClass<?>> {
    private final Matcher<? super String> simpleNameMatcher;

    public MatcherHasSimpleName(final Matcher<? super String> simpleNameMatcher) {
        this.simpleNameMatcher = simpleNameMatcher;
    }

    @Override public void describeTo(final Description description) {
        description.appendText("type that has simple name ").appendDescriptionOf(simpleNameMatcher);
    }

    @Override protected boolean matchesSafely(final ReflectedClass<?> item) {
        return simpleNameMatcher.matches(item.simpleName());
    }
}
