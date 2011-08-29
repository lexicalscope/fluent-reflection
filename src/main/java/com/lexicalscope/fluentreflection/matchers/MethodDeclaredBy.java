/**
 * 
 */
package com.lexicalscope.fluentreflection.matchers;

import org.hamcrest.Description;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

final class MethodDeclaredBy extends ReflectionMatcher<ReflectedMethod> {
    private final Class<?> declaringKlass;

    MethodDeclaredBy(Class<?> declaringKlass) {
        this.declaringKlass = declaringKlass;
    }

    @Override
    public boolean matchesSafely(final ReflectedMethod arg) {
        return arg.getDeclaringClass().getClassUnderReflection().equals(declaringKlass);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("method declared by ").appendValue(declaringKlass);
    }
}