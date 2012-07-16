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
class FluentClassImpl<T> extends AbstractFluentAccess<T> implements FluentClass<T> {
    FluentClassImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral,
            final ReflectedMembers<T> members) {
        super(reflectedTypeFactory, typeLiteral, members);
    }

    @Override public FluentClass<?> asType(final Matcher<FluentAccess<?>> typeMatcher) {
        if (typeMatcher.matches(this)) {
            return this;
        }
        return selectFirst(members.superclassesAndInterfaces(), typeMatcher);
    }

    @Override public FluentClass<T> boxedType() {
        if (isPrimitive()) {
            return reflectedTypeFactory.reflect(Primitives.wrap(klass));
        }
        return this;
    }

    @Override public FluentMethod staticMethod(final Matcher<? super FluentMethod> methodMatcher) {
        return method(allOf(isStatic(), methodMatcher));
    }

    @Override public FluentConstructor<T> constructor(
            final Matcher<? super FluentConstructor<?>> constructorMatcher) {
        return members.constructor(constructorMatcher);
    }

    @Override public List<FluentConstructor<T>> constructors(
            final Matcher<? super FluentConstructor<?>> constructorMatcher) {
        return members.constructors(constructorMatcher);
    }

    @Override public FluentObject<T> construct(final Object... args) {
        final FluentConstructor<T> constructor =
                constructor(hasArgumentList(convert(args, new ConvertObjectToClass())));

        if (constructor == null) {
            throw new ConstructorNotFoundRuntimeException(typeLiteral.getRawType());
        }

        return constructor.call(args);
    }

    @Override public FluentClass<T> unboxedType() {
        if (isUnboxable()) {
            return reflectedTypeFactory.reflect(Primitives.unwrap(klass));
        }
        return this;
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
