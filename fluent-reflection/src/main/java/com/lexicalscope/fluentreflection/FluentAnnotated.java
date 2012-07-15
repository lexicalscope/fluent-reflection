package com.lexicalscope.fluentreflection;

import java.lang.annotation.Annotation;

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

/**
 * Reflection access to an annotated program element.
 *
 * @author tim
 */
public interface FluentAnnotated {
    FluentClass<?> annotation(Matcher<? super FluentClass<?>> annotationMatcher);
    <A extends Annotation> A annotation(Class<A> annotationClass);

    boolean annotatedWith(Class<? extends Annotation> annotationClass);
    boolean annotatedWith(Matcher<? super FluentAccess<?>> annotationMatcher);
}