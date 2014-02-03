package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatcher.allOf;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static org.hamcrest.Matchers.hasItem;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hamcrest.Matcher;

import com.google.common.primitives.Primitives;
import com.google.inject.TypeLiteral;

/*
 * Copyright 2012 Tim Wood
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

public abstract class AbstractFluentAccess<T> implements FluentAccess<T> {
    protected final FluentAnnotatedImpl annotatedElement;
    protected final Class<T> klass;
    protected final ReflectedMembers<T> members;
    protected final ReflectedTypeFactory reflectedTypeFactory;
    protected final TypeLiteral<T> typeLiteral;

    AbstractFluentAccess(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral,
            final ReflectedMembers<T> members) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.klass = (Class<T>) typeLiteral.getRawType();
        this.typeLiteral = typeLiteral;
        this.members = members;
        this.annotatedElement = new FluentAnnotatedImpl(reflectedTypeFactory, klass);
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

    @Override public FluentClass<?> annotation(final Matcher<? super FluentClass<?>> annotationMatcher) {
        return annotatedElement.annotation(annotationMatcher);
    }

    @Override public boolean assignableFrom(final Class<?> otherKlass) {
        return klass.isAssignableFrom(otherKlass);
    }

    @Override public boolean assignableFromObject(final Object value) {
        return value == null
                || klass.isAssignableFrom(value.getClass())
                || canBeBoxed(value.getClass())
                || canBeUnboxed(value.getClass());
    }

    @Override public boolean assignableTo(final Class<?> otherKlass) {
        return otherKlass.isAssignableFrom(klass);
    }

    @Override public FluentObject<?> call(
            final Matcher<? super FluentMember> methodMatcher,
            final Object ... args)
    {
        return member(allOf(methodMatcher, canBeCalledWithArguments(args))).call(args);
    }

    @Override public FluentObject<?> call(final String name, final Object ... args)
    {
        return call(hasName(name), args);
    }

    @Override public boolean canBeBoxed(final Class<?> from) {
        return Primitives.isWrapperType(klass)
                && Primitives.unwrap(klass).isAssignableFrom(from);
    }

    @Override public boolean canBeUnboxed(final Class<?> from) {
        return isPrimitive()
                && Primitives.wrap(klass).isAssignableFrom(from);
    }

    @Override public Class<T> classUnderReflection() {
        return klass;
    }

    @Override public List<FluentField> declaredFields() {
        return members.declaredFields();
    }

    @Override public List<FluentMethod> declaredMethods() {
        return members.declaredMethods();
    }

    @Override public FluentField field(final Matcher<? super FluentMember> fieldMatcher) {
        return members.field(fieldMatcher);
    }

    @Override public FluentField field(final String fieldName) {
        return field(hasName(fieldName));
    }

    @Override public List<FluentField> fields() {
        return members.fields();
    }

    @Override public List<FluentField> fields(final Matcher<? super FluentField> fieldMatcher) {
        return members.fields(fieldMatcher);
    }

    @Override public List<FluentClass<?>> interfaces() {
        return members.superclassesAndInterfaces(isAnInterface());
    }

    @Override public boolean isInterface() {
        return typeLiteral.getRawType().isInterface();
    }

    @Override public boolean isPrimitive() {
        return klass.isPrimitive();
    }

    @Override public boolean isType(final Matcher<? super FluentClass<?>> typeMatcher) {
        if (typeMatcher.matches(this)) {
            return true;
        }
        return hasItem(typeMatcher).matches(members.superclassesAndInterfaces());
    }

    @Override public boolean isUnboxable() {
        return Primitives.isWrapperType(klass);
    }

    @Override public FluentMethod method(final Matcher<? super FluentMethod> methodMatcher) {
        return members.method(methodMatcher);
    }

    @Override public FluentMethod method(final String name) {
        return members.method(hasName(name));
    }

    @Override public List<FluentMethod> methods() {
        return members.methods();
    }

    @Override public List<FluentMethod> methods(final Matcher<? super FluentMethod> methodMatcher) {
        return members.methods(methodMatcher);
    }

    @Override public String name() {
        return klass.getName();
    }

    @Override public String simpleName() {
        return klass.getSimpleName();
    }

    @Override public List<FluentClass<?>> superclasses() {
        return members.superclassesAndInterfaces(not(isAnInterface()));
    }

    @Override public Type type() {
        return typeLiteral.getType();
    }

    @Override public FluentClass<?> typeArgument(final int typeParameter) {
        return reflectedTypeFactory.reflect(TypeLiteral.get(((ParameterizedType) typeLiteral.getType())
                .getActualTypeArguments()[typeParameter]));
    }

    @Override public FluentMember member(final Matcher<? super FluentMember> memberMatcher) {
        return members.member(memberMatcher);
    }
}
