package com.lexicalscope.fluentreflection;

import java.lang.reflect.Constructor;

import org.hamcrest.Description;

final class MatcherConstructorReflectingOn extends ReflectionMatcher<ReflectedConstructor<?>> {
    private final Constructor<?> constructor;

    public MatcherConstructorReflectingOn(final Constructor<?> constructor) {
        this.constructor = constructor;
    }

    @Override
    protected boolean matchesSafely(final ReflectedConstructor<?> item) {
        return item.memberUnderReflection().equals(constructor);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("reflecting on constructor ").appendValue(constructor);
    }
}
