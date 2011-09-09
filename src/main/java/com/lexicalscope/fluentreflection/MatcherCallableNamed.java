/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherCallableNamed extends ReflectionMatcher<ReflectedCallable> {
    private final String name;

    MatcherCallableNamed(final String name) {
        this.name = name;
    }

    @Override
    public boolean matchesSafely(final ReflectedCallable arg) {
        return arg.getName().startsWith(name);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable named ").appendValue(name);
    }
}