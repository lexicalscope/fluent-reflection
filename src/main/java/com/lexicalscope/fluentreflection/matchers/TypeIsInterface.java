/**
 * 
 */
package com.lexicalscope.fluentreflection.matchers;

import org.hamcrest.Description;

import com.lexicalscope.fluentreflection.ReflectedType;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

final class TypeIsInterface extends ReflectionMatcher<ReflectedType<?>> {
    @Override
    public boolean matchesSafely(final ReflectedType<?> arg) {
        return arg.isInterface();
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("type that is an interface");
    }
}