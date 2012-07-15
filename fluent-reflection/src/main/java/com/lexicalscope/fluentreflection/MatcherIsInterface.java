/**
 *
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherIsInterface extends ReflectionMatcher<FluentAccess<?>> {
    @Override
    public boolean matchesSafely(final FluentAccess<?> arg) {
        return arg.isInterface();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that is an interface");
    }
}