package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

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

class MatcherCallableHasVoidReturn extends ReflectionMatcher<ReflectedCallable> {
    @Override
    protected boolean matchesSafely(final ReflectedCallable item) {
        return item.returnType() == null;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable with no return type");
    }

    @Override
    public final boolean equals(final Object that) {
        return that != null && that.getClass().equals(this.getClass());
    }

    @Override
    public final int hashCode() {
        return MatcherCallableHasVoidReturn.class.hashCode();
    }
}