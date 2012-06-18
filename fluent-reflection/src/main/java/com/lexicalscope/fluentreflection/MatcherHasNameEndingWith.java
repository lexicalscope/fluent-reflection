/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherHasNameEndingWith extends ReflectionMatcher<FluentMember> {
    private final String suffix;

    MatcherHasNameEndingWith(final String suffix) {
        this.suffix = suffix;
    }

    @Override
    public boolean matchesSafely(final FluentMember arg) {
        return arg.name().endsWith(suffix);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable ending with ").appendValue(suffix);
    }
}