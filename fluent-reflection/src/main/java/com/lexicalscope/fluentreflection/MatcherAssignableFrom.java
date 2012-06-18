/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherAssignableFrom extends ReflectionMatcher<FluentClass<?>> {
    private final Class<?> klass;

    MatcherAssignableFrom(final Class<?> klass) {
        this.klass = klass;
    }

    public MatcherAssignableFrom(final FluentClass<?> klass) {
        this(klass.classUnderReflection());
    }

    @Override
    public boolean matchesSafely(final FluentClass<?> arg) {
        return arg.classUnderReflection().isAssignableFrom(klass);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type assignable from ").appendValue(klass);
    }
}