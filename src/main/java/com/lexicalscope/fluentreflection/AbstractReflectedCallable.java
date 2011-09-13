package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.*;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

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
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final AnnotatedElement annotatedElement;

    public AbstractReflectedCallable(
            final ReflectedTypeFactory reflectedTypeFactory,
            final AnnotatedElement annotatedElement) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.annotatedElement = annotatedElement;
    }

    @Override
    public ReflectedClass<?> annotation(final ReflectionMatcher<? super ReflectedClass<?>> annotationMatcher) {
        return selectFirst(annotations(), annotationMatcher);
    }

    private List<ReflectedClass<?>> annotations() {
        return convert(
                convert(annotatedElement.getAnnotations(), new ConvertAnnotationToClass()),
                new ConvertClassToReflectedType(reflectedTypeFactory));
    }
}
