package com.lexicalscope.fluentreflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

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

abstract class AbstractReflectedCallable implements ReflectedCallable {
    private final ReflectedAnnotated annotatedElement;

    public AbstractReflectedCallable(
            final ReflectedTypeFactory reflectedTypeFactory,
            final AnnotatedElement annotatedElement) {
        this.annotatedElement = new ReflectedAnnotatedImpl(reflectedTypeFactory, annotatedElement);
    }
    @Override public ReflectedClass<?> annotation(final ReflectionMatcher<? super ReflectedClass<?>> annotationMatcher) {
        return annotatedElement.annotation(annotationMatcher);
    }

    @Override public boolean annotatedWith(final Class<? extends Annotation> annotationClass) {
        return annotatedElement.annotatedWith(annotationClass);
    }

    @Override public <A extends Annotation> A annotation(final Class<A> annotationClass) {
        return annotatedElement.annotation(annotationClass);
    }
}