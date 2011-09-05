package com.lexicalscope.fluentreflection.matchers;

import org.hamcrest.Description;

import com.lexicalscope.fluentreflection.ReflectedConstructor;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

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
