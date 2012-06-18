/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherHasNameStartingWith extends ReflectionMatcher<FluentMember> {
    private final String prefix;

    MatcherHasNameStartingWith(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean matchesSafely(final FluentMember arg) {
        return arg.name().startsWith(prefix);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable starting with ").appendValue(prefix);
    }
}