package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectingOn;

import java.lang.annotation.Annotation;

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

class MatcherCallableAnnotatedWith extends ReflectionMatcher<ReflectedAnnotated> {
    private final Class<? extends Annotation> annotation;

    public MatcherCallableAnnotatedWith(final Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    @Override
    protected boolean matchesSafely(final ReflectedAnnotated item) {
        return item.annotation(reflectingOn(annotation)) != null;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable annotated with ").appendValue(annotation);

    }
}
