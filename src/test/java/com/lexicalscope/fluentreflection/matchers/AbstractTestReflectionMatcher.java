package com.lexicalscope.fluentreflection.matchers;

import static com.lexicalscope.fluentreflection.testhelpers.ListBuilder.list;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
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

    protected final ReflectedMethod method = context.mock(ReflectedMethod.class);
    protected final ReflectedType<?> type = context.mock(ReflectedType.class);
    private final Description description = new StringDescription();

    @Test
    public void matcherCanMatch() {
        setupMatchingCase();

        assertThat(target(), matcher());
    }

    @Test
    public void matcherCanFailToMatch() {
        setupFailingCase();

        assertThat(target(), not(matcher()));
    }

    @Test
    public void matcherDescriptionMakesSense() {
        assertHasDescription(matcher(), hasDescription());
    }

    protected abstract T target();

    protected abstract Matcher<String> hasDescription();

    protected abstract void setupMatchingCase();

    protected abstract void setupFailingCase();

    protected abstract ReflectionMatcher<T> matcher();

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

    protected final void whenTypeHasInterface(final Class<?> interfac3) {
        final ReflectedType<?> interfaceType = context.mock(ReflectedType.class, "interfaceType");
        context.checking(new Expectations() {
            {
                oneOf(type).getInterfaces();
                will(returnValue(list(interfaceType).$()));

                allowing(interfaceType).getClassUnderReflection();
                will(returnValue(interfac3));
            }
        });
    }

    protected final void whenTypeHasNoInterface() {
        context.checking(new Expectations() {
            {
                oneOf(type).getInterfaces();
                will(returnValue(emptyList()));
            }
        });
    }

    protected final void whenTypeIsInterface() {
        context.checking(new Expectations() {
            {
                oneOf(type).isInterface();
                will(returnValue(true));
            }
        });
    }

    protected final void whenTypeIsNotInterface() {
        context.checking(new Expectations() {
            {
                oneOf(type).isInterface();
                will(returnValue(false));
            }
        });
    }

    protected final void whenType(final Class<?> klass) {
        context.checking(new Expectations() {
            {
                oneOf(type).getClassUnderReflection();
                will(returnValue(klass));
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
