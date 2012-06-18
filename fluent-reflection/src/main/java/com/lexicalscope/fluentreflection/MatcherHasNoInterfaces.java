/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherHasNoInterfaces extends ReflectionMatcher<FluentClass<?>> {
    @Override
    public boolean matchesSafely(final FluentClass<?> arg) {
        return arg.interfaces().isEmpty();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that implements no interfaces");
    }
}