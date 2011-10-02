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
import static org.hamcrest.Matchers.not;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
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

    @Override public List<ReflectedClass<?>> interfaces() {
        return members.superclassesAndInterfaces(typeIsInterface());
    }

    @Override public List<ReflectedClass<?>> superclasses() {
        return members.superclassesAndInterfaces(not(typeIsInterface()));
    }

    @Override public boolean isInterface() {
        return klass.isInterface();
    }

    @Override public T constructRaw(final Object... args) {
        final ReflectedConstructor<T> constructor =
                constructor(callableHasArgumentList(convert(args, new ConvertObjectToClass())));

        if (constructor == null) {
            throw new ConstructorNotFoundRuntimeException(klass);
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
            return klass.equals(((ReflectedClassImpl<?>) that).klass);
        }
        return false;
    }

    @Override public int hashCode() {
        return klass.hashCode();
    }

    @Override public String toString() {
        return "ReflectedType<" + klass.getName() + ">";
    }

    @Override public boolean assignableFromObject(final Object value) {
        return value == null
                || klass.isAssignableFrom(value.getClass())
                || canBeBoxed(value)
                || canBeUnboxed(value);
    }

    private boolean canBeBoxed(final Object value) {
        return Primitives.isWrapperType(klass)
                && Primitives.unwrap(klass).isAssignableFrom(value.getClass());
    }

    private boolean canBeUnboxed(final Object value) {
        return klass.isPrimitive()
                && Primitives.wrap(klass).isAssignableFrom(value.getClass());
    }

    @Override public T convertType(final Object value) {
        if (value == null) {
            return null;
        } else if (isIterable() && Iterable.class.isAssignableFrom(value.getClass())) {
            final ArrayList<Object> result = new ArrayList<Object>();

            final TypeLiteral<?> desiredCollectionType =
                    TypeLiteral.get(((ParameterizedType) typeLiteral.getSupertype(Iterable.class).getType())
                            .getActualTypeArguments()[0]);

            final ReflectedClass<?> desiredCollectionReflectedType =
                    reflectedTypeFactory.reflect(desiredCollectionType);

            final Iterable<Object> values = (Iterable<Object>) value;
            for (final Object listItem : values) {
                result.add(desiredCollectionReflectedType.convertType(listItem));
            }

            return (T) result;
        } else if (assignableFromObject(value)) {
            return (T) value;
        } else if (canBeUnboxed(value)) {
            return (T) value;
        } else if (canBeBoxed(value)) {
            return (T) value;
        }

        final List<ReflectedMethod> valueOfMethods =
                methods(callableHasName("valueOf").and(callableHasArguments(value.getClass())).and(
                        callableHasReturnType(klass)));

        if (!valueOfMethods.isEmpty()) {
            return klass.cast(valueOfMethods.get(0).call(value));
        }

        final List<ReflectedConstructor<T>> constructors =
                constructors(callableHasArguments(value.getClass()));

        if (!constructors.isEmpty()) {
            return constructors.get(0).call(value);
        }

        throw new ClassCastException(String.format("cannot convert %s to %s", value.getClass(), klass));
    }

    private boolean isIterable() {
        return Iterable.class.isAssignableFrom(klass);
    }

    @Override public TypeLiteral<?> typeLiteralUnderReflection() {
        return typeLiteral;
    }
}
