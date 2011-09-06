package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


class ArgumentMatcherCountMatcher extends ReflectionMatcher<ReflectedConstructor<?>> {
    private final int expectedArgumentCount;

    public ArgumentMatcherCountMatcher(final int expectedArgumentCount) {
        this.expectedArgumentCount = expectedArgumentCount;
    }

    @Override
    protected boolean matchesSafely(final ReflectedConstructor<?> item) {
        return item.argumentCount() == expectedArgumentCount;
    }

    @Override
    public void describeTo(final Description description) {
        // TODO Auto-generated method stub
    }
}
