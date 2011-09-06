/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class TypeIsInterfaceMatcher extends ReflectionMatcher<ReflectedType<?>> {
    @Override
    public boolean matchesSafely(final ReflectedType<?> arg) {
        return arg.isInterface();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that is an interface");
    }
}