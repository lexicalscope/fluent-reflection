/**
 * 
 */
package com.lexicalscope.fluentreflection.matchers;

import org.hamcrest.Description;

import com.lexicalscope.fluentreflection.ReflectedType;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

final class ReflectedTypeReflectingOn extends ReflectionMatcher<ReflectedType<?>> {
    private final Class<?> klass;

    ReflectedTypeReflectingOn(Class<?> klass) {
        this.klass = klass;
    }

    @Override
    public boolean matchesSafely(final ReflectedType<?> arg) {
        return arg.getClassUnderReflection().equals(klass);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("reflecting on type ").appendValue(klass);
    }
}