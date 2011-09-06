/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class TypeHasNoSuperclassesMatcher extends ReflectionMatcher<ReflectedType<?>> {
    @Override
    public boolean matchesSafely(final ReflectedType<?> arg) {
        return arg.superclasses().isEmpty();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that extends no superclasses");
    }
}