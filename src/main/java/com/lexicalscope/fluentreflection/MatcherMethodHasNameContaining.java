/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherMethodHasNameContaining extends ReflectionMatcher<ReflectedMethod> {
    private final CharSequence substring;

    MatcherMethodHasNameContaining(CharSequence substring) {
        this.substring = substring;
    }

    @Override
    public boolean matchesSafely(final ReflectedMethod arg) {
        return arg.getName().contains(substring);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("method containing ").appendValue(substring);
    }
}