/**
 * 
 */
package com.lexicalscope.fluentreflection;

import java.util.regex.Pattern;

import org.hamcrest.Description;

final class MatcherCallableHasNameMatching extends ReflectionMatcher<ReflectedCallable> {
    private final String regex;
    private final Pattern pattern;

    MatcherCallableHasNameMatching(final String regex) {
        this.regex = regex;
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean matchesSafely(final ReflectedCallable arg) {
        return pattern.matcher(arg.getName()).matches();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable matching ").appendValue(regex);
    }
}