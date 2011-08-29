/**
 * 
 */
package com.lexicalscope.fluentreflection.matchers;

import org.hamcrest.Description;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

final class MethodHasNameContaining extends ReflectionMatcher<ReflectedMethod> {
    private final CharSequence substring;

    MethodHasNameContaining(CharSequence substring) {
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