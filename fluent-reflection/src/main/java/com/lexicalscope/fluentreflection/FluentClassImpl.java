package com.lexicalscope.fluentreflection;

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

import static ch.lambdaj.Lambda.*;
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

/**
 * Not thread safe
 *
 * @author tim
 *
 * @param <T>
 */
class FluentClassImpl<T> implements FluentClass<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final Class<T> klass;
    private final ReflectedMembers<T> members;
    private final TypeLiteral<T> typeLiteral;
    private final FluentAnnotatedImpl annotatedElement;

    FluentClassImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral,
            final ReflectedMembers<T> members) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.klass = (Class<T>) typeLiteral.getRawType();
        this.typeLiteral = typeLiteral;
        this.members = members;
        this.annotatedElement = new FluentAnnotatedImpl(reflectedTypeFactory, klass);
    }

    @Override public Class<T> classUnderReflection() {
        return klass;
    }

    @Override public List<FluentMethod> methods(final Matcher<? super FluentMethod> methodMatcher) {
        return members.methods(methodMatcher);
    }

    @Override public FluentMethod method(final Matcher<? super FluentMethod> methodMatcher) {
        return members.method(methodMatcher);
    }

    @Override public FluentMethod method(final String name) {
        return members.method(hasName(name));
    }

    @Override public FluentMethod staticMethod(final Matcher<? super FluentMethod> methodMatcher) {
        return method(allOf(isStatic(), methodMatcher));
    }

    @Override public List<FluentMethod> methods() {
        return members.methods();
    }

    @Override public List<FluentMethod> declaredMethods() {
        return members.declaredMethods();
    }

    @SuppressWarnings("unchecked") @Override public FluentObject<T> call(final String name, final Object ... args)
    {
        return (FluentObject<T>) method(hasName(name).and(canBeCalledWithArguments(args))).call(args);
    }

    @Override public List<ReflectedField> fields() {
        return members.fields();
    }

    @Override public List<ReflectedField> fields(final ReflectionMatcher<? super ReflectedField> fieldMatcher) {
        return members.fields(fieldMatcher);
    }

    @Override public ReflectedField field(final ReflectionMatcher<FluentMember> fieldMatcher) {
        return members.field(fieldMatcher);
    }

    @Override public List<ReflectedField> declaredFields() {
        return members.declaredFields();
    }

    @Override public List<FluentClass<?>> interfaces() {
        return members.superclassesAndInterfaces(isAnInterface());
    }

    @Override public List<FluentClass<?>> superclasses() {
        return members.superclassesAndInterfaces(not(isAnInterface()));
    }

    @Override public FluentClass<?> asType(final Matcher<FluentClass<?>> typeMatcher) {
        if (typeMatcher.matches(this)) {
            return this;
        }
        return selectFirst(members.superclassesAndInterfaces(), typeMatcher);
    }

    @Override public boolean isType(final ReflectionMatcher<FluentClass<?>> typeMatcher) {
        if (typeMatcher.matches(this)) {
            return true;
        }
        return hasItem(typeMatcher).matches(members.superclassesAndInterfaces());
    }

    @Override public boolean isInterface() {
        return typeLiteral.getRawType().isInterface();
    }

    @Override public T constructRaw(final Object... args) {
        final FluentConstructor<T> constructor =
                constructor(hasArgumentList(convert(args, new ConvertObjectToClass())));

        if (constructor == null) {
            throw new ConstructorNotFoundRuntimeException(typeLiteral.getRawType());
        }

        return constructor.callRaw(args);
    }

    @Override public FluentObject<T> construct(final Object... args) {
        final T newInstance = constructRaw(args);
        return reflectedTypeFactory.reflect(typeLiteral, newInstance);
    }

    @Override public List<FluentConstructor<T>> constructors(
            final Matcher<? super FluentConstructor<?>> constructorMatcher) {
        return members.constructors(constructorMatcher);
    }

    @Override public FluentConstructor<T> constructor(
            final Matcher<? super FluentConstructor<?>> constructorMatcher) {
        return members.constructor(constructorMatcher);
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

    @Override public boolean canBeBoxed(final Class<?> from) {
        return Primitives.isWrapperType(klass)
                && Primitives.unwrap(klass).isAssignableFrom(from);
    }

    @Override public boolean canBeUnboxed(final Class<?> from) {
        return isPrimitive()
                && Primitives.wrap(klass).isAssignableFrom(from);
    }

    @Override public FluentClass<T> boxedType() {
        if (isPrimitive()) {
            return reflectedTypeFactory.reflect(Primitives.wrap(klass));
        }
        return this;
    }

    @Override public FluentClass<T> unboxedType() {
        if (isUnboxable()) {
            return reflectedTypeFactory.reflect(Primitives.unwrap(klass));
        }
        return this;
    }

    @Override public boolean isPrimitive() {
        return klass.isPrimitive();
    }

    @Override public boolean isUnboxable() {
        return Primitives.isWrapperType(klass);
    }

    @Override public FluentClass<?> typeArgument(final int typeParameter) {
        return reflectedTypeFactory.reflect(TypeLiteral.get(((ParameterizedType) typeLiteral.getType())
                .getActualTypeArguments()[typeParameter]));
    }

    @Override public String name() {
        return klass.getName();
    }

    @Override public String simpleName() {
        return klass.getSimpleName();
    }

    @Override public FluentClass<?> annotation(final Matcher<? super FluentClass<?>> annotationMatcher) {
        return annotatedElement.annotation(annotationMatcher);
    }

    @Override public <A extends Annotation> A annotation(final Class<A> annotationClass) {
        return annotatedElement.annotation(annotationClass);
    }

    @Override public boolean annotatedWith(final Class<? extends Annotation> annotationClass) {
        return annotatedElement.annotatedWith(annotationClass);
    }

    @Override public boolean annotatedWith(final Matcher<? super FluentClass<?>> annotationMatcher) {
        return annotatedElement.annotatedWith(annotationMatcher);
    }

    @Override public Type type() {
        return typeLiteral.getType();
    }

    @Override public boolean equals(final Object that) {
        if (that != null && that.getClass().equals(this.getClass())) {
            return typeLiteral.equals(((FluentClassImpl<?>) that).typeLiteral);
        }
        return false;
    }

    @Override public int hashCode() {
        return typeLiteral.hashCode();
    }

    @Override public String toString() {
        return typeLiteral.toString();
    }
}
