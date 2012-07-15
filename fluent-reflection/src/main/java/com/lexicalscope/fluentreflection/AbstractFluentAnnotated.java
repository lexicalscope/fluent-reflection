package com.lexicalscope.fluentreflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

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

abstract class AbstractFluentAnnotated implements FluentAnnotated {
    private final FluentAnnotated annotatedElement;

    public AbstractFluentAnnotated(
            final ReflectedTypeFactory reflectedTypeFactory,
            final AnnotatedElement annotatedElement) {
        this.annotatedElement = new FluentAnnotatedImpl(reflectedTypeFactory, annotatedElement);
    }

    @Override public FluentClass<?> annotation(final Matcher<? super FluentClass<?>> annotationMatcher) {
        return annotatedElement.annotation(annotationMatcher);
    }

    @Override public boolean annotatedWith(final Class<? extends Annotation> annotationClass) {
        return annotatedElement.annotatedWith(annotationClass);
    }

    @Override public boolean annotatedWith(final Matcher<? super FluentAccess<?>> annotationMatcher) {
        return annotatedElement.annotatedWith(annotationMatcher);
    }

    @Override public <A extends Annotation> A annotation(final Class<A> annotationClass) {
        return annotatedElement.annotation(annotationClass);
    }
}
