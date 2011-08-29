/**
 * 
 */
package com.lexicalscope.fluentreflection.matchers;

import java.util.regex.Pattern;

import org.hamcrest.Description;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

final class MethodHasNameMatching extends ReflectionMatcher<ReflectedMethod> {
    private final String regex;
    private final Pattern pattern;

    MethodHasNameMatching(String regex) {
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