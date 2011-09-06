/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherTypeHasNoInterfaces extends ReflectionMatcher<ReflectedType<?>> {
    @Override
    public boolean matchesSafely(final ReflectedType<?> arg) {
        return arg.interfaces().isEmpty();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that implements no interfaces");
    }
}