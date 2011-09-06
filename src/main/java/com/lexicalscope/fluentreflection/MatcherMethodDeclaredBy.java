/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;


final class MatcherMethodDeclaredBy extends ReflectionMatcher<ReflectedMethod> {
    private final Class<?> declaringKlass;

    MatcherMethodDeclaredBy(Class<?> declaringKlass) {
        this.declaringKlass = declaringKlass;
    }

    @Override
    public boolean matchesSafely(final ReflectedMethod arg) {
        return arg.getDeclaringClass().classUnderReflection().equals(declaringKlass);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("method declared by ").appendValue(declaringKlass);
    }
}