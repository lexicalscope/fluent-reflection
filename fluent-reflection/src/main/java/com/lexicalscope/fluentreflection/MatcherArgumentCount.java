package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherArgumentCount extends ReflectionMatcher<FluentMember> {
    private final int expectedArgumentCount;

    public MatcherArgumentCount(final int expectedArgumentCount) {
        this.expectedArgumentCount = expectedArgumentCount;
    }

    @Override
    protected boolean matchesSafely(final FluentMember item) {
        return item.argCount() == expectedArgumentCount;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable with ").appendValue(expectedArgumentCount).appendText(" arguments");
    }
}
