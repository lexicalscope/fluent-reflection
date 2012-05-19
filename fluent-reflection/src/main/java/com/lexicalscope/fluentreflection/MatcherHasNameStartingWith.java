/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherHasNameStartingWith extends ReflectionMatcher<ReflectedMember> {
    private final String prefix;

    MatcherHasNameStartingWith(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean matchesSafely(final ReflectedMember arg) {
        return arg.getName().startsWith(prefix);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable starting with ").appendValue(prefix);
    }
}