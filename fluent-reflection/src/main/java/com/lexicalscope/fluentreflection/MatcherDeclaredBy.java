/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

final class MatcherDeclaredBy extends ReflectionMatcher<ReflectedCallable> {
    private final Matcher<? super ReflectedClass<?>> declaringKlassMatcher;

    MatcherDeclaredBy(final Matcher<? super ReflectedClass<?>> declaringKlassMatcher) {
        this.declaringKlassMatcher = declaringKlassMatcher;
    }

    @Override public boolean matchesSafely(final ReflectedCallable arg) {
        return declaringKlassMatcher.matches(arg.declaringClass());
    }

    @Override public void describeTo(final Description description) {
        description.appendText("callable declared by ").appendDescriptionOf(declaringKlassMatcher);
    }
}