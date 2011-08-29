/**
 * 
 */
package com.lexicalscope.fluentreflection.matchers;

import org.hamcrest.Description;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

final class MethodHasNameEndingWith extends ReflectionMatcher<ReflectedMethod> {
    private final String suffix;

    MethodHasNameEndingWith(String suffix) {
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