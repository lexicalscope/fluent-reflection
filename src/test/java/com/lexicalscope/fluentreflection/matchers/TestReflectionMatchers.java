package com.lexicalscope.fluentreflection.matchers;

import static com.lexicalscope.fluentreflection.matchers.ReflectionMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jmock.Expectations;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;
import com.lexicalscope.fluentreflection.testhelpers.JUnitRuleMockery;

public class TestReflectionMatchers {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final ReflectedMethod method = context.mock(ReflectedMethod.class);
    private final Description description = new StringDescription();

    @Test
    public void methodHasNameStartingWithMatchesWhenNameStartsWith() {
        whenMethodHasName("abcdef");

        assertThat(method, methodHasNameStartingWith("abc"));
    }

    @Test
    public void methodHasNameStartingWithNotMatchesWhenNameNotStartsWith() {
        whenMethodHasName("defabc");

        assertThat(method, not(methodHasNameStartingWith("abc")));
    }

    @Test
    public void methodHasNameStartingWithDescriptionMakesSense() {
        assertHasDescription(methodHasNameStartingWith("abc"), equalTo("method starting with \"abc\""));
    }

    @Test
    public void methodHasNameEndingWithMatchesWhenNameEndsWith() {
        whenMethodHasName("defabc");

        assertThat(method, methodHasNameEndingWith("abc"));
    }

    @Test
    public void methodHasNameEndingWithNotMatchesWhenNameNotEndsWith() {
        whenMethodHasName("abcdef");

        assertThat(method, not(methodHasNameEndingWith("abc")));
    }

    @Test
    public void methodHasNameEndingWithDescriptionMakesSense() {
        assertHasDescription(methodHasNameEndingWith("abc"), equalTo("method ending with \"abc\""));
    }

    @Test
    public void methodHasNameMatchingMatchesWhenNameMatches() {
        whenMethodHasName("abcdef");

        assertThat(method, methodHasNameMatching(".+bc.+"));
    }

    @Test
    @Ignore
    public void testContains() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testNamed() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testWithArguments() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testDeclaredBy() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testHasNoInterfaces() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testHasInterface() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testIsInterface() {
        fail("Not yet implemented");
    }

    @Test
    @Ignore
    public void testReflectingOn() {
        fail("Not yet implemented");
    }

    private void whenMethodHasName(final String methodName) {
        context.checking(new Expectations() {
            {
                oneOf(method).getName();
                will(returnValue(methodName));
            }
        });
    }

    private void assertHasDescription(
            final ReflectionMatcher<ReflectedMethod> matcherUnderTest,
            final Matcher<String> descriptionMatcher) {
        matcherUnderTest.describeTo(description);
        assertThat(description, hasToString(descriptionMatcher));
    }
}
