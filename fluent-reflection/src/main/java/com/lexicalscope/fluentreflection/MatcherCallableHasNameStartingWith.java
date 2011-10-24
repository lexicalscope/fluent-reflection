/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherCallableHasNameStartingWith extends ReflectionMatcher<ReflectedCallable> {
    private final String prefix;

    MatcherCallableHasNameStartingWith(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean matchesSafely(final ReflectedCallable arg) {
        return arg.getName().startsWith(prefix);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable starting with ").appendValue(prefix);
    }
}