/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherAnyReflectedType extends ReflectionMatcher<ReflectedClass<?>> {
    @Override
    public boolean matchesSafely(final ReflectedClass<?> arg) {
        return true;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("any type");
    }
}