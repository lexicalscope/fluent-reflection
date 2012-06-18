package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherIsStatic extends ReflectionMatcher<FluentMember> {
    @Override
    protected boolean matchesSafely(final FluentMember item) {
        return item.isStatic();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("static element");
    }
}
