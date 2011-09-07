/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherMethodHasNameEndingWith extends ReflectionMatcher<ReflectedMethod> {
    private final String suffix;

    MatcherMethodHasNameEndingWith(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public boolean matchesSafely(final ReflectedMethod arg) {
        return arg.getName().endsWith(suffix);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("method ending with ").appendValue(suffix);
    }
}