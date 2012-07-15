package com.lexicalscope.fluentreflection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Test;

public abstract class AbstractTestReflectionMatcherNoMocks<T> {
    private final Description description = new StringDescription();

    protected final void assertHasDescription(
            final ReflectionMatcher<?> matcherUnderTest,
            final Matcher<String> descriptionMatcher) {
        matcherUnderTest.describeTo(description);
        assertThat(description, hasToString(descriptionMatcher));
    }

    protected T failingTarget() {
        return target();
    }

    protected abstract Matcher<String> hasDescription();

    protected abstract ReflectionMatcher<? super T> matcher() throws Throwable;

    @Test public final void matcherCanFailToMatch() throws Throwable {
        setupFailingCase();

        assertThat(failingTarget(), not(matcher()));
    }

    @Test public final void matcherCanMatch() throws Throwable {
        setupMatchingCase();

        assertThat(target(), matcher());
    }

    @Test public final void matcherDescriptionMakesSense() throws Throwable {
        assertHasDescription(matcher(), hasDescription());
    }

    protected void setupFailingCase() throws Throwable {}

    protected void setupMatchingCase() throws Throwable {}

    protected abstract T target();
}
