package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

class MatcherMethodIsStatic extends ReflectionMatcher<ReflectedElement> {
    @Override
    protected boolean matchesSafely(final ReflectedElement item) {
        return item.isStatic();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("static element");
    }
}
