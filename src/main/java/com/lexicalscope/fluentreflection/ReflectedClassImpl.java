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

import static ch.lambdaj.Lambda.convert;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static org.hamcrest.Matchers.hasItem;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hamcrest.Matcher;

import ch.lambdaj.Lambda;

import com.google.common.primitives.Primitives;
import com.google.inject.TypeLiteral;

/**
 * Not thread safe
 * 
 * @author tim
 * 
 * @param <T>
 */
final class ReflectedClassImpl<T> implements ReflectedClass<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final Class<T> klass;
    private final ReflectedMembers<T> members;
    private final TypeLiteral<T> typeLiteral;

    ReflectedClassImpl(final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral,
            final ReflectedMembers<T> members) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.klass = (Class<T>) typeLiteral.getRawType();
        this.typeLiteral = typeLiteral;
        this.members = members;
    }

    @Override public Class<T> classUnderReflection() {
        return klass;
    }

    @Override public List<ReflectedMethod> methods(final Matcher<? super ReflectedMethod> methodMatcher) {
        return members.methods(methodMatcher);
    }

    @Override public ReflectedMethod method(final Matcher<? super ReflectedMethod> methodMatcher) {
        return members.method(methodMatcher);
    }

    @Override public ReflectedMethod staticMethod(final Matcher<? super ReflectedMethod> methodMatcher) {
        return method(ReflectionMatchers.methodIsStatic().and(methodMatcher));
    }

    @Override public List<ReflectedMethod> methods() {
        return members.methods();
    }

    @Override public List<ReflectedMethod> declaredMethods() {
        return members.declaredMethods();
    }

    @Override public List<ReflectedClass<?>> interfaces() {
        return members.superclassesAndInterfaces(typeIsInterface());
    }

    @Override public List<ReflectedClass<?>> superclasses() {
        return members.superclassesAndInterfaces(not(typeIsInterface()));
    }

    @Override public ReflectedClass<?> asType(final Matcher<ReflectedClass<?>> typeMatcher) {
        if (typeMatcher.matches(this)) {
            return this;
        }
        return Lambda.selectFirst(members.superclassesAndInterfaces(), typeMatcher);
    }

    @Override public boolean isType(final ReflectionMatcher<ReflectedClass<?>> typeMatcher) {
        if (typeMatcher.matches(this)) {
            return true;
        }
        return hasItem(typeMatcher).matches(members.superclassesAndInterfaces());
    }

    @Override public boolean isInterface() {
        return typeLiteral.getRawType().isInterface();
    }

    @Override public T constructRaw(final Object... args) {
        final ReflectedConstructor<T> constructor =
                constructor(callableHasArgumentList(convert(args, new ConvertObjectToClass())));

        if (constructor == null) {
            throw new ConstructorNotFoundRuntimeException(typeLiteral.getRawType());
        }

        return constructor.call(args);
    }

    @Override public ReflectedObject<T> construct(final Object... args) {
        final T newInstance = constructRaw(args);
        return reflectedTypeFactory.reflect(typeLiteral, newInstance);
    }

    @Override public List<ReflectedConstructor<T>> constructors(
            final Matcher<? super ReflectedConstructor<?>> constructorMatcher) {
        return members.constructors(constructorMatcher);
    }

    @Override public ReflectedConstructor<T> constructor(
            final Matcher<? super ReflectedConstructor<?>> constructorMatcher) {
        return members.constructor(constructorMatcher);
    }

    @Override public boolean equals(final Object that) {
        if (that != null && that.getClass().equals(this.getClass())) {
            return typeLiteral.equals(((ReflectedClassImpl<?>) that).typeLiteral);
        }
        return false;
    }

    @Override public int hashCode() {
        return typeLiteral.hashCode();
    }

    @Override public String toString() {
        return "ReflectedType<" + typeLiteral + ">";
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

    @Override public ReflectedClass<T> boxedType() {
        if (isPrimitive()) {
            return reflectedTypeFactory.reflect(Primitives.wrap(klass));
        }
        return this;
    }

    @Override public boolean isPrimitive() {
        return klass.isPrimitive();
    }

    @Override public ReflectedClass<?> typeArgument(final int typeParameter) {
        return reflectedTypeFactory.reflect(TypeLiteral.get(((ParameterizedType) typeLiteral.getType())
                .getActualTypeArguments()[typeParameter]));
    }
}
