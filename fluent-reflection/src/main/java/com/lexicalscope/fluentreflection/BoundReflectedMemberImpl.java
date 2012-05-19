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

abstract class BoundReflectedMemberImpl implements ReflectedMember {
    private final ReflectedMember member;
    private final Object instance;

    public BoundReflectedMemberImpl(final ReflectedMember member, final Object instance) {
        if (member.isStatic()) {
            throw new IllegalArgumentException("cannot bind static member " + member);
        }
        this.member = member;
        this.instance = instance;
    }

    @Override public final int argumentCount() {
        return member.argumentCount();
    }

    @Override public final List<ReflectedClass<?>> argumentTypes() {
        return new ArrayList<ReflectedClass<?>>(member.argumentTypes());
    }

    @Override public final Object call(final Object... args) {
        final Object[] argsWithInstance = new Object[args.length + 1];
        argsWithInstance[0] = instance;
        arraycopy(args, 0, argsWithInstance, 1, args.length);
        return member.call(argsWithInstance);
    }

    @Override public final ReflectedClass<?> declaringClass() {
        return member.declaringClass();
    }

    @Override public final String getName() {
        return member.getName();
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

    @Override public final ReflectedClass<?> type() {
        return member.type();
    }

    @Override public final ReflectedClass<?> annotation(final Matcher<? super ReflectedClass<?>> annotationMatcher) {
        return member.annotation(annotationMatcher);
    }

    @Override public final boolean annotatedWith(final Class<? extends Annotation> annotationClass) {
        return member.annotatedWith(annotationClass);
    }

    @Override public final <A extends Annotation> A annotation(final Class<A> annotationClass) {
        return member.annotation(annotationClass);
    }

    @Override public final String propertyName() {
        return member.propertyName();
    }

    @Override public final <T> ReflectedQuery<T> castResultTo(final Class<T> returnType) {
        return new ReflectedQuery<T>() {
            @Override public T call(final Object... args) {
                return returnType.cast(BoundReflectedMemberImpl.this.call(args));
            }
        };
    }

    @Override public final boolean equals(final Object that) {
        if(this == that) {
            return true;
        } else  if(that != null && that.getClass().equals(this.getClass())) {
            final BoundReflectedMemberImpl castedThat = (BoundReflectedMemberImpl) that;
            return castedThat.instance.equals(this.instance) && castedThat.member.equals(this.member);
        }
        return false;
    }
}
