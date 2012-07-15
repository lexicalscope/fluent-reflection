package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.*;
import static com.lexicalscope.fluentreflection.ReflectionMatcher.allOf;
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

final class FluentObjectImpl<T> implements FluentObject<T> {
    private final FluentClass<T> reflect;
    private final T instance;
    private final ReflectedTypeFactory reflectedTypeFactory;

    public FluentObjectImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final FluentClass<T> reflect,
            final T instance) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.reflect = reflect;
        this.instance = instance;
    }

    @Override public boolean annotatedWith(final Class<? extends Annotation> annotationClass) {
        return reflect.annotatedWith(annotationClass);
    }

    @Override public boolean annotatedWith(final Matcher<? super FluentAccess<?>> annotationMatcher) {
        return reflect.annotatedWith(annotationMatcher);
    }

    @Override public <A extends Annotation> A annotation(final Class<A> annotationClass) {
        return reflect.annotation(annotationClass);
    }

    @Override public FluentClass<?> annotation(final Matcher<? super FluentClass<?>> annotationMatcher) {
        return reflect.annotation(annotationMatcher);
    }

    @Override public <V> V as(final Class<V> asType) {
        return asType.cast(value());
    }

    @Override public boolean assignableFrom(final Class<?> klass) {
        return reflect.assignableFrom(klass);
    }

    @Override public boolean assignableFromObject(final Object value) {
        return reflect.assignableFromObject(value);
    }

    @Override public boolean assignableTo(final Class<?> klass) {
        return reflect.assignableTo(klass);
    }

    private List<FluentMethod> bind(final List<FluentMethod> methods) {
        return convert(
                select(methods, isNotStatic()),
                new ConvertReflectedMethodToBoundReflectedMethod(reflectedTypeFactory, instance));
    }

    private List<FluentField> bindFields(final List<FluentField> fields) {
        return convert(
                select(fields, isNotStatic()),
                new ConvertReflectedFieldToBoundReflectedField(reflectedTypeFactory, instance));
    }

    private List<FluentField> boundDeclaredFields() {
        return bindFields(reflect.declaredFields());
    }

    private List<FluentMethod> boundDeclaredMethods() {
        return bind(reflect.declaredMethods());
    }

    private List<FluentField> boundFields() {
        return bindFields(reflect.fields());
    }

    private List<FluentMethod> boundMethods() {
        return bind(reflect.methods());
    }

    @Override public FluentClass<T> boxedType() {
        return reflect.boxedType();
    }

    @Override public FluentObject<?> call(final Matcher<? super FluentMethod> methodMatcher, final Object... args) {
        return method(allOf(methodMatcher, canBeCalledWithArguments(args))).call(args);
    }

    @Override public FluentObject<?> call(final String name, final Object ... args) {
        final ReflectionMatcher<FluentMember> methodMatcher = hasName(name);
        return call(methodMatcher, args);
    }

    @Override public boolean canBeBoxed(final Class<?> from) {
        return reflect.canBeBoxed(from);
    }

    @Override public boolean canBeUnboxed(final Class<?> from) {
        return reflect.canBeUnboxed(from);
    }

    @Override public Class<T> classUnderReflection() {
        return reflect.classUnderReflection();
    }

    @Override public List<FluentField> declaredFields() {
        return boundDeclaredFields();
    }

    @Override public List<FluentMethod> declaredMethods() {
        return boundDeclaredMethods();
    }

    @Override public FluentField field(final ReflectionMatcher<FluentMember> fieldMatcher) {
        final FluentField selectedField = selectFirst(fields(), fieldMatcher);
        if (selectedField == null) {
            throw new FieldNotFoundException(instance.getClass(), fieldMatcher);
        }
        return selectedField;
    }

    @Override public List<FluentField> fields() {
        return boundFields();
    }

    @Override public List<FluentField> fields(final ReflectionMatcher<? super FluentField> fieldMatcher) {
        return select(boundFields(), fieldMatcher);
    }

    @Override public List<FluentClass<?>> interfaces() {
        return reflect.interfaces();
    }

    @Override public boolean isPrimitive() {
        return reflect.isPrimitive();
    }

    @Override public boolean isType(final ReflectionMatcher<FluentClass<?>> typeMatcher) {
        return reflect.isType(typeMatcher);
    }

    @Override public boolean isUnboxable() {
        return reflect.isUnboxable();
    }

    @Override public FluentMethod method(final Matcher<? super FluentMethod> methodMatcher) {
        final FluentMethod method = selectFirst(boundMethods(), methodMatcher);
        if(method == null) {
            throw new MethodNotFoundException(reflect.classUnderReflection(), methodMatcher);
        }
        return method;
    }

    @Override public FluentMethod method(final String name) {
        return method(hasName(name));
    }

    @Override public List<FluentMethod> methods() {
        return boundMethods();
    }

    @Override public List<FluentMethod> methods(final Matcher<? super FluentMethod> methodMatcher) {
        return select(boundMethods(), methodMatcher);
    }

    @Override public String name() {
        return reflect.name();
    }

    @Override public FluentClass<T> reflectedClass() {
        return reflect;
    }

    @Override public String simpleName() {
        return reflect.simpleName();
    }

    @Override public List<FluentClass<?>> superclasses() {
        return reflect.superclasses();
    }

    @Override public Type type() {
        return reflect.type();
    }

    @Override public FluentClass<?> typeArgument(final int typeParameter) {
        return reflect.typeArgument(typeParameter);
    }

    @Override public FluentClass<T> unboxedType() {
        return reflect.unboxedType();
    }

    @Override public T value() {
        return instance;
    }
}