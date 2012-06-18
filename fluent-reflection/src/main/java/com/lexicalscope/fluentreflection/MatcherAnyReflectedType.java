/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherAnyReflectedType extends ReflectionMatcher<FluentClass<?>> {
    @Override
    public boolean matchesSafely(final FluentClass<?> arg) {
        return true;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("any type");
    }
}