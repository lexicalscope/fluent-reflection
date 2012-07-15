/**
 *
 */
package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.select;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectingOn;

import org.hamcrest.Description;


final class MatcherHasInterface extends ReflectionMatcher<FluentAccess<?>> {
    private final Class<?> interfac3;

    MatcherHasInterface(final Class<?> interfac3) {
        this.interfac3 = interfac3;
    }

    @Override
    public boolean matchesSafely(final FluentAccess<?> arg) {
        return !select(
                arg.interfaces(),
                reflectingOn(interfac3)).isEmpty();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that implements interface ").appendValue(interfac3);
    }
}