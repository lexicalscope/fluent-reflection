package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

class MatcherMethodIsStatic extends ReflectionMatcher<ReflectedMethod> {
    @Override
    protected boolean matchesSafely(final ReflectedMethod item) {
        return item.isStatic();
    }

    @Override
    public void describeTo(final Description description) {
        // TODO Auto-generated method stub

    }

}
