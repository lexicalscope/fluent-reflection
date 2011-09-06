/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherMethodNamed extends ReflectionMatcher<ReflectedMethod> {
    private final String name;

    MatcherMethodNamed(String name) {
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