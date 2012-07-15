/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;

final class MatcherAnyFluentType extends ReflectionMatcher<FluentAccess<?>> {
    @Override
    public boolean matchesSafely(final FluentAccess<?> arg) {
        return true;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("any type");
    }
}