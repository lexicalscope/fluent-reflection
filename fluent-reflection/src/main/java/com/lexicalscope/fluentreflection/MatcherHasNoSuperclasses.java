/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherHasNoSuperclasses extends ReflectionMatcher<ReflectedClass<?>> {
    @Override
    public boolean matchesSafely(final ReflectedClass<?> arg) {
        return arg.superclasses().isEmpty();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that extends no superclasses");
    }
}