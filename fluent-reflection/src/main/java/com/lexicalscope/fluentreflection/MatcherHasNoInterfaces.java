/**
 *
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherHasNoInterfaces extends ReflectionMatcher<FluentAccess<?>> {
    @Override
    public boolean matchesSafely(final FluentAccess<?> arg) {
        return arg.interfaces().isEmpty();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that implements no interfaces");
    }
}