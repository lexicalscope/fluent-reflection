/**
 *
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherAssignableTo extends ReflectionMatcher<FluentAccess<?>> {
    private final Class<?> klass;

    MatcherAssignableTo(final Class<?> klass) {
        this.klass = klass;
    }

    public MatcherAssignableTo(final FluentAccess<?> klass) {
        this(klass.classUnderReflection());
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type assignable to ").appendValue(klass);
    }

    @Override
    public boolean matchesSafely(final FluentAccess<?> arg) {
        return arg.assignableTo(klass);
    }
}