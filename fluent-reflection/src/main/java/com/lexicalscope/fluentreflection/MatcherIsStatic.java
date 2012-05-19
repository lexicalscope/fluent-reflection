package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

class MatcherIsStatic extends ReflectionMatcher<ReflectedMember> {
    @Override
    protected boolean matchesSafely(final ReflectedMember item) {
        return item.isStatic();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("static element");
    }
}
