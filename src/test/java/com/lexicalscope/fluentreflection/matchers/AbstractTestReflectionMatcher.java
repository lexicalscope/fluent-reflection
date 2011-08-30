package com.lexicalscope.fluentreflection.matchers;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.jmock.Expectations;
import org.junit.Rule;
import org.junit.Test;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectedType;
import com.lexicalscope.fluentreflection.ReflectionMatcher;
import com.lexicalscope.fluentreflection.testhelpers.JUnitRuleMockery;

public abstract class AbstractTestReflectionMatcher<T> {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    private final ReflectedMethod method = context.mock(ReflectedMethod.class);
    private final Description description = new StringDescription();

    @Test
    public void matcherCanMatch() {
        setupMatchingCase();

        assertThat(method, matcher());
    }

    @Test
    public void matcherCanFailToMatch() {
        setupFailingCase();

        assertThat(method, not(matcher()));
    }

    @Test
    public void matcherDescriptionMakesSense() {
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

    protected final void whenMethodDeclaredBy(final Class<?> declaringClass) {
        final ReflectedType<?> declaringType = context.mock(ReflectedType.class, "declaringType");
        context.checking(new Expectations() {
            {
                oneOf(method).getDeclaringClass();
                will(returnValue(declaringType));

                oneOf(declaringType).getClassUnderReflection();
                will(returnValue(declaringClass));
            }
        });
    }

    protected final void whenMethodHasArguments(final Class<?>... arguments) {
        final ReflectedType<?>[] argumentTypes = new ReflectedType<?>[arguments.length];
        for (int i = 0; i < argumentTypes.length; i++) {
            argumentTypes[i] = context.mock(ReflectedType.class, "argument " + i + ": " + arguments[i].getSimpleName());
        }

        context.checking(new Expectations() {
            {
                oneOf(method).getArgumentTypes();
                will(returnValue(asList(argumentTypes)));

                for (int i = 0; i < argumentTypes.length; i++) {
                    oneOf(argumentTypes[i]).getClassUnderReflection();
                    will(returnValue(arguments[i]));
                }
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
