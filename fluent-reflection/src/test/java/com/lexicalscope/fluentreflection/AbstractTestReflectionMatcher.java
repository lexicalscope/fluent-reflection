package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ListBuilder.list;
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

public abstract class AbstractTestReflectionMatcher<T> {
    @Rule public final JUnitRuleMockery context = new JUnitRuleMockery();

    protected final FluentMethod method = context.mock(FluentMethod.class);
    protected final FluentConstructor<?> constructor = context.mock(FluentConstructor.class);
    protected final FluentClass<?> type = context.mock(FluentClass.class);
    protected final FluentMember callable = context.mock(FluentMember.class);
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

    protected final void whenMethodDeclaredBy(final Class<?> declaringClass) {
        final FluentClass<?> declaringType = context.mock(FluentClass.class, "declaringType");
        context.checking(new Expectations() {
            {
                oneOf(method).declarer();
                will(returnValue(declaringType));

                oneOf(declaringType).classUnderReflection();
                will(returnValue(declaringClass));
            }
        });
    }

    protected final void whenMethodHasArguments(final Class<?>... arguments) {
        final FluentClass<?>[] argumentTypes = new FluentClass<?>[arguments.length];
        for (int i = 0; i < argumentTypes.length; i++) {
            argumentTypes[i] =
                    context.mock(FluentClass.class, "argument " + i + ": " + arguments[i].getSimpleName());
        }

        context.checking(new Expectations() {
            {
                oneOf(method).argCount();
                will(returnValue(arguments.length));

                allowing(method).args();
                will(returnValue(asList(argumentTypes)));

                for (int i = 0; i < argumentTypes.length; i++) {
                    allowing(argumentTypes[i]).assignableFrom(arguments[i]);
                    will(returnValue(true));
                }
            }
        });

    }

    protected final void whenMethodHasName(final String methodName) {
        context.checking(new Expectations() {
            {
                oneOf(method).name();
                will(returnValue(methodName));
            }
        });
    }

    protected final void whenType(final Class<?> klass) {
        context.checking(new Expectations() {
            {
                oneOf(type).classUnderReflection();
                will(returnValue(klass));
            }
        });
    }

    protected final void whenTypeHasInterface(final Class<?> interfac3) {
        final FluentClass<?> interfaceType = context.mock(FluentClass.class, "interfaceType");
        context.checking(new Expectations() {
            {
                oneOf(type).interfaces();
                will(returnValue(list(interfaceType).$()));

                allowing(interfaceType).classUnderReflection();
                will(returnValue(interfac3));
            }
        });
    }

    protected final void whenTypeHasNoInterface() {
        context.checking(new Expectations() {
            {
                oneOf(type).interfaces();
                will(returnValue(emptyList()));
            }
        });
    }

    protected final void whenTypeHasNoSuperclass() {
        context.checking(new Expectations() {
            {
                oneOf(type).superclasses();
                will(returnValue(emptyList()));
            }
        });
    }

    protected final void whenTypeHasSuperclass(final Class<?> klass) {
        final FluentClass<?> superclassType = context.mock(FluentClass.class, "superclassType");
        context.checking(new Expectations() {
            {
                oneOf(type).superclasses();
                will(returnValue(list(superclassType).$()));

                allowing(superclassType).classUnderReflection();
                will(returnValue(klass));
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
}
