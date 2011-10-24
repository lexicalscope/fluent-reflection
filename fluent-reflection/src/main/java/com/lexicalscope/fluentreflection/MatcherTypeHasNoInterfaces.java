/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherTypeHasNoInterfaces extends ReflectionMatcher<ReflectedClass<?>> {
    @Override
    public boolean matchesSafely(final ReflectedClass<?> arg) {
        return arg.interfaces().isEmpty();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that implements no interfaces");
    }
}