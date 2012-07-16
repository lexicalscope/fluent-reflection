package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.google.inject.TypeLiteral;

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

final class FluentObjectImpl<T> extends FluentClassImpl<T> implements FluentObject<T> {
    private final T instance;

    FluentObjectImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final TypeLiteral<T> typeLiteral,
            final ReflectedMembers<T> members,
            final T instance) {
        super(reflectedTypeFactory, typeLiteral, members);
        this.instance = instance;
    }

    @Override public <V> V as(final Class<V> asType) {
        return asType.cast(value());
    }

    @Override public boolean equals(final Object that) {
        if(that != null && that.getClass().equals(getClass()))
        {
            final FluentObjectImpl<?> castedThat = (FluentObjectImpl<?>) that;
            return new EqualsBuilder().append(instance, castedThat.instance).isEquals();
        }
        return false;
    }

    @Override public FluentField field(final String fieldName) {
        return field(hasName(fieldName));
    }

    @Override public int hashCode() {
        return new HashCodeBuilder().append(instance).toHashCode();
    }

    @Override public String toString() {
        return instance != null ? instance.toString() : "" + null;
    }

    @Override public T value() {
        return instance;
    }

    @Override public FluentClass<T> reflectedClass() {
        return this;
    }
}
