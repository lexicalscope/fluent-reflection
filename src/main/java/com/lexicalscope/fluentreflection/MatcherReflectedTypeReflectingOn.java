/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherReflectedTypeReflectingOn extends ReflectionMatcher<ReflectedType<?>> {
    private final Class<?> klass;

    MatcherReflectedTypeReflectingOn(Class<?> klass) {
        this.klass = klass;
    }

    @Override
    public boolean matchesSafely(final ReflectedType<?> arg) {
        return arg.classUnderReflection().equals(klass);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("reflecting on type ").appendValue(klass);
    }
}