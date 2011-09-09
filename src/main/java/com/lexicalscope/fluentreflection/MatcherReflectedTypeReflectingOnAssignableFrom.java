/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherReflectedTypeReflectingOnAssignableFrom extends ReflectionMatcher<ReflectedType<?>> {
    private final Class<?> klass;

    MatcherReflectedTypeReflectingOnAssignableFrom(final Class<?> klass) {
        this.klass = klass;
    }

    @Override
    public boolean matchesSafely(final ReflectedType<?> arg) {
        return arg.classUnderReflection().isAssignableFrom(klass);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("reflecting on type assignable from ").appendValue(klass);
    }
}