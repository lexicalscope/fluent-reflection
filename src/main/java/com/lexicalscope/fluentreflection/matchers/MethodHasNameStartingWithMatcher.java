/**
 * 
 */
package com.lexicalscope.fluentreflection.matchers;

import org.hamcrest.Description;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

final class MethodHasNameStartingWithMatcher extends ReflectionMatcher<ReflectedMethod> {
    private final String prefix;

    MethodHasNameStartingWithMatcher(String prefix) {
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