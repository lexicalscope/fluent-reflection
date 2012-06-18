/**
 * 
 */
package com.lexicalscope.fluentreflection;

import java.util.regex.Pattern;

import org.hamcrest.Description;

final class MatcherHasNameMatching extends ReflectionMatcher<FluentMember> {
    private final String regex;
    private final Pattern pattern;

    MatcherHasNameMatching(final String regex) {
        this.regex = regex;
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean matchesSafely(final FluentMember arg) {
        return pattern.matcher(arg.name()).matches();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable matching ").appendValue(regex);
    }
}