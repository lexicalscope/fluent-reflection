/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherTypeIsInterface extends ReflectionMatcher<ReflectedClass<?>> {
    @Override
    public boolean matchesSafely(final ReflectedClass<?> arg) {
        return arg.isInterface();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that is an interface");
    }
}