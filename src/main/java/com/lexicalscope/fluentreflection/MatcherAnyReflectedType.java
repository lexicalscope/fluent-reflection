/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherAnyReflectedType extends ReflectionMatcher<ReflectedType<?>> {
    @Override
    public boolean matchesSafely(final ReflectedType<?> arg) {
        return true;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("reflected type ");
    }
}