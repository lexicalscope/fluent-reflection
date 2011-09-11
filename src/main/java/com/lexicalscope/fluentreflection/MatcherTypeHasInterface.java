/**
 * 
 */
package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.select;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectedTypeReflectingOn;

import org.hamcrest.Description;


final class MatcherTypeHasInterface extends ReflectionMatcher<ReflectedClass<?>> {
    private final Class<?> interfac3;

    MatcherTypeHasInterface(final Class<?> interfac3) {
        this.interfac3 = interfac3;
    }

    @Override
    public boolean matchesSafely(final ReflectedClass<?> arg) {
        return !select(
                arg.interfaces(),
                reflectedTypeReflectingOn(interfac3)).isEmpty();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that implements interface ").appendValue(interfac3);
    }
}