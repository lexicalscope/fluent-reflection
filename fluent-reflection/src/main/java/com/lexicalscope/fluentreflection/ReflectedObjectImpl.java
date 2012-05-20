package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.*;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
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

    @Override public Class<T> classUnderReflection() {
        return reflect.classUnderReflection();
    }

    @Override public ReflectedClass<T> reflectedClass() {
        return reflect;
    }

    @Override public ReflectedMethod method(final Matcher<? super ReflectedMethod> methodMatcher) {
        return selectFirst(boundMethods(), methodMatcher);
    }

    @Override public ReflectedMethod method(final String name) {
        return method(hasName(name));
    }

    @Override public List<ReflectedMethod> methods() {
        return boundMethods();
    }

    @Override public List<ReflectedMethod> declaredMethods() {
        return boundDeclaredMethods();
    }

    @Override public List<ReflectedMethod> methods(final Matcher<? super ReflectedMethod> methodMatcher) {
        return select(boundMethods(), methodMatcher);
    }

    private List<ReflectedMethod> boundMethods() {
        return bind(reflect.methods());
    }

    private List<ReflectedMethod> bind(final List<ReflectedMethod> methods) {
        return convert(
                select(methods, isNotStatic()),
                new ConvertReflectedMethodToBoundReflectedMethod(instance));
    }

    private List<ReflectedMethod> boundDeclaredMethods() {
        return bind(reflect.declaredMethods());
    }

    @Override public List<ReflectedField> fields(final ReflectionMatcher<? super ReflectedField> fieldMatcher) {
        return select(boundFields(), fieldMatcher);
    }

    @Override public List<ReflectedField> declaredFields() {
        return boundDeclaredFields();
    }

    private List<ReflectedField> boundFields() {
        return bindFields(reflect.fields());
    }

    private List<ReflectedField> boundDeclaredFields() {
        return bindFields(reflect.declaredFields());
    }

    private List<ReflectedField> bindFields(final List<ReflectedField> fields) {
        return convert(
                select(fields, isNotStatic()),
                new ConvertReflectedFieldToBoundReflectedField(instance));
    }

    @Override public List<ReflectedField> fields() {
        return boundFields();
    }

    @Override public ReflectedField field(final ReflectionMatcher<ReflectedMember> fieldMatcher) {
        return selectFirst(fields(), fieldMatcher);
    }

    @Override public boolean canBeBoxed(final Class<?> from) {
        return reflect.canBeBoxed(from);
    }

    @Override public boolean canBeUnboxed(final Class<?> from) {
        return reflect.canBeUnboxed(from);
    }

    @Override public ReflectedClass<?> annotation(final Matcher<? super ReflectedClass<?>> annotationMatcher) {
        return reflect.annotation(annotationMatcher);
    }

    @Override public boolean annotatedWith(final Class<? extends Annotation> annotationClass) {
        return reflect.annotatedWith(annotationClass);
    }

    @Override public <A extends Annotation> A annotation(final Class<A> annotationClass) {
        return reflect.annotation(annotationClass);
    }

    @Override public boolean isPrimitive() {
        return reflect.isPrimitive();
    }

    @Override public ReflectedClass<T> boxedType() {
        return reflect.boxedType();
    }

    @Override public boolean assignableFromObject(final Object value) {
        return reflect.assignableFromObject(value);
    }

    @Override public boolean assignableTo(final Class<?> klass) {
        return reflect.assignableTo(klass);
    }

    @Override public ReflectedClass<?> typeArgument(final int typeParameter) {
        return reflect.typeArgument(typeParameter);
    }

    @Override public String name() {
        return reflect.name();
    }

    @Override public String simpleName() {
        return reflect.simpleName();
    }

    @Override public Type type() {
        return reflect.type();
    }

    @Override public List<ReflectedClass<?>> interfaces() {
        return reflect.interfaces();
    }

    @Override public List<ReflectedClass<?>> superclasses() {
        return reflect.superclasses();
    }

    @Override public boolean isType(final ReflectionMatcher<ReflectedClass<?>> typeMatcher) {
        return reflect.isType(typeMatcher);
    }
}
