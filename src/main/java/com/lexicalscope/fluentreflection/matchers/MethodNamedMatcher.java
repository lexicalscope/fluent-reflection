/**
 * 
 */
package com.lexicalscope.fluentreflection.matchers;

import org.hamcrest.Description;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

final class MethodNamedMatcher extends ReflectionMatcher<ReflectedMethod> {
    private final String name;

    MethodNamedMatcher(String name) {
        this.name = name;
    }

    @Override
    public boolean matchesSafely(final ReflectedMethod arg) {
        return arg.getName().startsWith(name);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("method named ").appendValue(name);
    }
}