package com.lexicalscope.fluentreflection.matchers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;
import com.lexicalscope.fluentreflection.testhelpers.JUnitRuleMockery;

public abstract class AbstractTestReflectionMatcher<T> {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final ReflectedMethod method = context.mock(ReflectedMethod.class);
    private final Description description = new StringDescription();

    @Test
    public void methodHasNameStartingWithMatchesWhenNameStartsWith() {
        setupMatchingCase();

        assertThat(method, matcher());
    }

    @Test
    public void methodHasNameStartingWithNotMatchesWhenNameNotStartsWith() {
        setupFailingCase();

        assertThat(method, not(matcher()));
    }

    @Test
    public void methodHasNameStartingWithDescriptionMakesSense() {
        assertHasDescription(matcher(), hasDescription());
    }

    protected abstract Matcher<String> hasDescription();

    protected abstract void setupMatchingCase();

    protected abstract void setupFailingCase();

    protected abstract ReflectionMatcher<ReflectedMethod> matcher();

    protected final void whenMethodHasName(final String methodName) {
        context.checking(new Expectations() {
            {
                oneOf(method).getName();
                will(returnValue(methodName));
            }
        });
    }

    protected final void assertHasDescription(
            final ReflectionMatcher<?> matcherUnderTest,
            final Matcher<String> descriptionMatcher) {
        matcherUnderTest.describeTo(description);
        assertThat(description, hasToString(descriptionMatcher));
    }
}
