package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

/*
 * Copyright 2012 Tim Wood
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

final class MatcherPropertyName extends ReflectionMatcher<ReflectedMember> {
    private final String propertyName;

    public MatcherPropertyName(final String propertyName) {
        this.propertyName = propertyName;
    }

    @Override public void describeTo(final Description description) {
        description.appendText("member with property name ").appendValue(propertyName);
    }

    @Override protected boolean matchesSafely(final ReflectedMember item) {
        return item.propertyName().equals(propertyName);
    }
}
