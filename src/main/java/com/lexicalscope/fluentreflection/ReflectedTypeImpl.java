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

import java.util.List;

import org.hamcrest.Matcher;

/**
 * Not thread safe
 * 
 * @author tim
 * 
 * @param <T>
 */
final class ReflectedTypeImpl<T> implements ReflectedType<T> {
    private final ReflectedTypeFactory reflectedTypeFactory;
    private final Class<T> klass;
    private final ReflectedMembers<T> members;

    public ReflectedTypeImpl(final ReflectedTypeFactory reflectedTypeFactory,
                             final Class<T> klass,
                             final ReflectedMembers<T> members) {
        this.reflectedTypeFactory = reflectedTypeFactory;
        this.klass = klass;
        this.members = members;
    }

    @Override
    public Class<T> classUnderReflection() {
        return klass;
    }

    @Override
    public List<ReflectedMethod> methods(final Matcher<? super ReflectedMethod> methodMatcher) {
        return members.methods(methodMatcher);
    }

    @Override
    public ReflectedMethod method(final Matcher<? super ReflectedMethod> methodMatcher) {
        return members.method(methodMatcher);
    }

    @Override
    public ReflectedMethod staticMethod(final Matcher<? super ReflectedMethod> methodMatcher) {
        return method(ReflectionMatchers.staticMethod().and(methodMatcher));
    }

    @Override
    public List<ReflectedMethod> methods() {
        return members.methods();
    }

    @Override
    public List<ReflectedType<?>> interfaces() {
        return members.superclassesAndInterfaces(typeIsInterface());
    }

    @Override
    public List<ReflectedType<?>> superclasses() {
        return members.superclassesAndInterfaces(not(typeIsInterface()));
    }

    @Override
    public boolean isInterface() {
        return klass.isInterface();
    }

    @Override
    public T constructRaw(final Object... args) {
        final ReflectedConstructor<T> constructor =
                constructor(callableHasArguments(convert(args, new ConvertObjectToClass())));

        if (constructor == null) {
            throw new ConstructorNotFoundRuntimeException(klass);
        }

        return constructor.call(args);
    }

    @Override
    public List<ReflectedConstructor<T>> constructors(
            final Matcher<? super ReflectedConstructor<?>> constructorMatcher) {
        return members.constructors(constructorMatcher);
    }

    @Override
    public ReflectedConstructor<T> constructor(
            final Matcher<? super ReflectedConstructor<?>> constructorMatcher) {
        return members.constructor(constructorMatcher);
    }

    @Override
    public boolean equals(final Object that) {
        if (that != null && that.getClass().equals(this.getClass())) {
            return klass.equals(((ReflectedTypeImpl<?>) that).klass);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return klass.hashCode();
    }

    @Override
    public String toString() {
        return "ReflectedType<" + klass.getName() + ">";
    }
}
