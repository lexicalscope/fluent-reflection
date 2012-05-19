package com.lexicalscope.fluentreflection;

import static java.lang.System.arraycopy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
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

class BoundReflectedFieldImpl implements ReflectedField {
    private final ReflectedField field;
    private final Object instance;

    public BoundReflectedFieldImpl(
            final ReflectedField field,
            final Object instance) {
        if (field.isStatic()) {
            throw new IllegalArgumentException("cannot bind static field " + field);
        }
        this.field = field;
        this.instance = instance;
    }

    @Override public ReflectedClass<?> declaringClass() {
        return field.declaringClass();
    }

    @Override public String getName() {
        return field.getName();
    }

    @Override public boolean isStatic() {
        return false;
    }

    @Override public Visibility visibility() {
        return field.visibility();
    }

    @Override public boolean isFinal() {
        return field.isFinal();
    }

    @Override public ReflectedClass<?> annotation(final Matcher<? super ReflectedClass<?>> annotationMatcher) {
        return field.annotation(annotationMatcher);
    }

    @Override public boolean annotatedWith(final Class<? extends Annotation> annotationClass) {
        return field.annotatedWith(annotationClass);
    }

    @Override public <A extends Annotation> A annotation(final Class<A> annotationClass) {
        return field.annotation(annotationClass);
    }

    @Override public String propertyName() {
        return field.propertyName();
    }

    @Override public Field fieldUnderReflection() {
        return field.fieldUnderReflection();
    }

    @Override public int argumentCount() {
        return 0;
    }

    @Override public List<ReflectedClass<?>> argumentTypes() {
        return new ArrayList<ReflectedClass<?>>();
    }

    @Override public ReflectedClass<?> type() {
        return field.type();
    }

    @Override public Object call(final Object... args) {
        final Object[] argsWithInstance = new Object[args.length + 1];
        argsWithInstance[0] = instance;
        arraycopy(args, 0, argsWithInstance, 1, args.length);
        return field.call(argsWithInstance);
    }

    @Override public <T> ReflectedQuery<T> castResultTo(final Class<T> returnType) {
        return new ReflectedQuery<T>() {
            @Override public T call(final Object... args) {
                return returnType.cast(BoundReflectedFieldImpl.this.call(args));
            }
        };
    }

    @Override public String toString() {
        return String.format("%s in %s", field, instance);
    }
}
