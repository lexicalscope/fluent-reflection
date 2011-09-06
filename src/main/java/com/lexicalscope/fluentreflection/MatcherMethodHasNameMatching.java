/**
 * 
 */
package com.lexicalscope.fluentreflection;

import java.util.regex.Pattern;

import org.hamcrest.Description;


final class MatcherMethodHasNameMatching extends ReflectionMatcher<ReflectedMethod> {
    private final String regex;
    private final Pattern pattern;

    MatcherMethodHasNameMatching(String regex) {
        this.regex = regex;
        pattern = Pattern.compile(regex);
    }

    @Override
    public boolean matchesSafely(final ReflectedMethod arg) {
        return pattern.matcher(arg.getName()).matches();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("method matching ").appendValue(regex);
    }
}