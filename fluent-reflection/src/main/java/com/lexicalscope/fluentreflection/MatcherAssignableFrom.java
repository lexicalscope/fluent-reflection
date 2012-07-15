/**
 *
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherAssignableFrom extends ReflectionMatcher<FluentAccess<?>> {
    private final Class<?> klass;

    MatcherAssignableFrom(final Class<?> klass) {
        this.klass = klass;
    }

    public MatcherAssignableFrom(final FluentAccess<?> klass) {
        this(klass.classUnderReflection());
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type assignable from ").appendValue(klass);
    }

    @Override
    public boolean matchesSafely(final FluentAccess<?> arg) {
        return arg.assignableFrom(klass);
    }
}