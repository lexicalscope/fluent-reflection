package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

class MatcherIsStatic extends ReflectionMatcher<ReflectedCallable> {
    @Override
    protected boolean matchesSafely(final ReflectedCallable item) {
        return item.isStatic();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("static element");
    }
}
