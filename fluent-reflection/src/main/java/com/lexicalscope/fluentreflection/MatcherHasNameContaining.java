/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherHasNameContaining extends ReflectionMatcher<FluentMember> {
    private final CharSequence substring;

    MatcherHasNameContaining(final CharSequence substring) {
        this.substring = substring;
    }

    @Override
    public boolean matchesSafely(final FluentMember arg) {
        return arg.name().contains(substring);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable containing ").appendValue(substring);
    }
}