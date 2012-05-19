/**
 * 
 */
package com.lexicalscope.fluentreflection;

import java.util.regex.Pattern;

import org.hamcrest.Description;

final class MatcherHasNameMatching extends ReflectionMatcher<ReflectedMember> {
    private final String regex;
    private final Pattern pattern;

    MatcherHasNameMatching(final String regex) {
        this.regex = regex;
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean matchesSafely(final ReflectedMember arg) {
        return pattern.matcher(arg.getName()).matches();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable matching ").appendValue(regex);
    }
}