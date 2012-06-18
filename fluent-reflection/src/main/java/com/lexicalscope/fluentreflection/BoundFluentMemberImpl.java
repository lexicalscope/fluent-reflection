package com.lexicalscope.fluentreflection;

import static java.lang.System.arraycopy;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hamcrest.Matcher;

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

abstract class BoundFluentMemberImpl implements FluentMember {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final FluentMember member;
    private final Object instance;

    public BoundFluentMemberImpl(
            final ReflectedTypeFactory reflectedTypeFactory,
            final FluentMember member,
            final Object instance) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        if (member.isStatic()) {
            throw new IllegalArgumentException("cannot bind static member " + member);
        }
        this.member = member;
        this.instance = instance;
    }

    @Override public final int argCount() {
        return member.argCount();
    }

    @Override public final List<FluentClass<?>> args() {
        return new ArrayList<FluentClass<?>>(member.args());
    }

    @SuppressWarnings("unchecked") @Override public FluentObject<?> call(final Object... args) {
        final Object object = callRaw(args);
        if(object == null) {
            return null;
        }
        return reflectedTypeFactory.reflect((Class) object.getClass(), object);
    }

    @Override public final Object callRaw(final Object... args) {
        final Object[] argsWithInstance = new Object[args.length + 1];
        argsWithInstance[0] = instance;
        arraycopy(args, 0, argsWithInstance, 1, args.length);
        return member.callRaw(argsWithInstance);
    }

    @Override public final FluentClass<?> declarer() {
        return member.declarer();
    }

    @Override public final String name() {
        return member.name();
    }

    @Override public final boolean isStatic() {
        return false;
    }

    @Override public final Visibility visibility() {
        return member.visibility();
    }

    @Override public final boolean isFinal() {
        return member.isFinal();
    }

    @Override public final int hashCode() {
        return new HashCodeBuilder().append(instance).append(member).toHashCode();
    }

    @Override public final FluentClass<?> type() {
        return member.type();
    }

    @Override public final FluentClass<?> annotation(final Matcher<? super FluentClass<?>> annotationMatcher) {
        return member.annotation(annotationMatcher);
    }

    @Override public final <A extends Annotation> A annotation(final Class<A> annotationClass) {
        return member.annotation(annotationClass);
    }

    @Override public final boolean annotatedWith(final Class<? extends Annotation> annotationClass) {
        return member.annotatedWith(annotationClass);
    }

    @Override public boolean annotatedWith(final Matcher<? super FluentClass<?>> annotationMatcher) {
        return member.annotatedWith(annotationMatcher);
    }

    @Override public final String property() {
        return member.property();
    }

    @Override public final <T> Call<T> as(final Class<T> returnType) {
        return new AbstractCall<T>(reflectedTypeFactory) {
            @Override public T callRaw(final Object... args) {
                return returnType.cast(BoundFluentMemberImpl.this.callRaw(args));
            }
        };
    }

    @Override public final boolean equals(final Object that) {
        if(this == that) {
            return true;
        } else  if(that != null && that.getClass().equals(this.getClass())) {
            final BoundFluentMemberImpl castedThat = (BoundFluentMemberImpl) that;
            return castedThat.instance.equals(this.instance) && castedThat.member.equals(this.member);
        }
        return false;
    }
}
