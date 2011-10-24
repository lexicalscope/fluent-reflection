/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherCallableDeclaredBy extends ReflectionMatcher<ReflectedCallable> {
    private final Class<?> declaringKlass;

    MatcherCallableDeclaredBy(final Class<?> declaringKlass) {
        this.declaringKlass = declaringKlass;
    }

    @Override
    public boolean matchesSafely(final ReflectedCallable arg) {
        return arg.declaringClass().classUnderReflection().equals(declaringKlass);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable declared by ").appendValue(declaringKlass);
    }
}