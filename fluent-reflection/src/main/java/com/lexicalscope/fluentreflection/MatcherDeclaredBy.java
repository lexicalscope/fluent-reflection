/**
 *
 */
package com.lexicalscope.fluentreflection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

final class MatcherDeclaredBy extends ReflectionMatcher<FluentMember> {
    private final Matcher<? super FluentClass<?>> declaringKlassMatcher;

    MatcherDeclaredBy(final Matcher<? super FluentClass<?>> declaringKlassMatcher) {
        this.declaringKlassMatcher = declaringKlassMatcher;
    }

    @Override public boolean matchesSafely(final FluentMember arg) {
        return declaringKlassMatcher.matches(arg.declarer());
    }

    @Override public void describeTo(final Description description) {
        description.appendText("callable declared by ").appendDescriptionOf(declaringKlassMatcher);
    }
}