package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.*;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.methodIsNotStatic;

import java.util.List;

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

class ReflectedObjectImpl<T> implements ReflectedObject<T> {
    private final ReflectedClass<T> reflect;
    private final T instance;

    public ReflectedObjectImpl(
            final ReflectedTypeFactoryImpl reflectedTypeFactoryImpl,
            final ReflectedClass<T> reflect,
            final T instance) {
        this.reflect = reflect;
        this.instance = instance;
    }

    @Override
    public Class<T> classUnderReflection() {
        return reflect.classUnderReflection();
    }

    @Override
    public ReflectedClass<T> reflectedClass() {
        return reflect;
    }

    @Override
    public ReflectedMethod method(final Matcher<? super ReflectedMethod> methodMatcher) {
        return selectFirst(boundMethods(), methodIsNotStatic().and(methodMatcher));
    }

    @Override
    public List<ReflectedMethod> methods() {
        return boundMethods();
    }

    @Override
    public List<ReflectedMethod> methods(final Matcher<? super ReflectedMethod> methodMatcher) {
        return select(boundMethods(), methodMatcher);
    }

    private List<ReflectedMethod> boundMethods() {
        return convert(
                select(reflect.methods(), methodIsNotStatic()),
                new ConvertReflectedMethodToBoundReflectedMethod(instance));
    }
}
