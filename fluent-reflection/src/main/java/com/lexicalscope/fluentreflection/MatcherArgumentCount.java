package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

class MatcherArgumentCount extends ReflectionMatcher<ReflectedMember> {
    private final int expectedArgumentCount;

    public MatcherArgumentCount(final int expectedArgumentCount) {
        this.expectedArgumentCount = expectedArgumentCount;
    }

    @Override
    protected boolean matchesSafely(final ReflectedMember item) {
        return item.argumentCount() == expectedArgumentCount;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable with ").appendValue(expectedArgumentCount).appendText(" arguments");
    }
}
