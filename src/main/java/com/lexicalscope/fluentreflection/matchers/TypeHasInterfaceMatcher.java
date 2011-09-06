/**
 * 
 */
package com.lexicalscope.fluentreflection.matchers;

import static ch.lambdaj.Lambda.select;
import static com.lexicalscope.fluentreflection.matchers.ReflectionMatchers.reflectedTypeReflectingOn;

import org.hamcrest.Description;

import com.lexicalscope.fluentreflection.ReflectedType;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

final class TypeHasInterfaceMatcher extends ReflectionMatcher<ReflectedType<?>> {
    private final Class<?> interfac3;

    TypeHasInterfaceMatcher(final Class<?> interfac3) {
        this.interfac3 = interfac3;
    }

    @Override
    public boolean matchesSafely(final ReflectedType<?> arg) {
        return !select(
                arg.interfaces(),
                reflectedTypeReflectingOn(interfac3)).isEmpty();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that implements interface ").appendValue(interfac3);
    }
}