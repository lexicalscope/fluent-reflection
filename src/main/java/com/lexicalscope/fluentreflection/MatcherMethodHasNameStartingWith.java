/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherMethodHasNameStartingWith extends ReflectionMatcher<ReflectedMethod> {
    private final String prefix;

    MatcherMethodHasNameStartingWith(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public boolean matchesSafely(final ReflectedMethod arg) {
        return arg.getName().startsWith(prefix);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("method starting with ").appendValue(prefix);
    }
}