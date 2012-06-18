/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherIsInterface extends ReflectionMatcher<FluentClass<?>> {
    @Override
    public boolean matchesSafely(final FluentClass<?> arg) {
        return arg.isInterface();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that is an interface");
    }
}