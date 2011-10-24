/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherCallableHasNameEndingWith extends ReflectionMatcher<ReflectedCallable> {
    private final String suffix;

    MatcherCallableHasNameEndingWith(final String suffix) {
        this.suffix = suffix;
    }

    @Override
    public boolean matchesSafely(final ReflectedCallable arg) {
        return arg.getName().endsWith(suffix);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable ending with ").appendValue(suffix);
    }
}