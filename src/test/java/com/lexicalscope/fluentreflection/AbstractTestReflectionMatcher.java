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
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    protected final ReflectedMethod method = context.mock(ReflectedMethod.class);
    protected final ReflectedClass<?> type = context.mock(ReflectedClass.class);
    protected final ReflectedCallable callable = context.mock(ReflectedCallable.class);
    private final Description description = new StringDescription();

    @Test
    public void matcherCanMatch() {
        setupMatchingCase();

        assertThat(target(), matcher());
    }

    @Test
    public void matcherCanFailToMatch() {
        setupFailingCase();

        assertThat(failingTarget(), not(matcher()));
    }

    @Test
    public void matcherDescriptionMakesSense() {
        assertHasDescription(matcher(), hasDescription());
    }

    protected abstract T target();

    protected abstract Matcher<String> hasDescription();

    protected void setupMatchingCase() {
    }

    protected void setupFailingCase() {

    }

    protected abstract ReflectionMatcher<T> matcher();

    protected T failingTarget() {
        return target();
    }

    protected final void whenMethodHasName(final String methodName) {
        context.checking(new Expectations() {
            {
                oneOf(method).getName();
                will(returnValue(methodName));
            }
        });
    }

    protected final void whenMethodDeclaredBy(final Class<?> declaringClass) {
        final ReflectedClass<?> declaringType = context.mock(ReflectedClass.class, "declaringType");
        context.checking(new Expectations() {
            {
                oneOf(method).declaringClass();
                will(returnValue(declaringType));

                oneOf(declaringType).classUnderReflection();
                will(returnValue(declaringClass));
            }
        });
    }

    protected final void whenMethodHasArguments(final Class<?>... arguments) {
        final ReflectedClass<?>[] argumentTypes = new ReflectedClass<?>[arguments.length];
        for (int i = 0; i < argumentTypes.length; i++) {
            argumentTypes[i] =
                    context.mock(ReflectedClass.class, "argument " + i + ": " + arguments[i].getSimpleName());
        }

        context.checking(new Expectations() {
            {
                oneOf(method).argumentCount();
                will(returnValue(arguments.length));

                allowing(method).argumentTypes();
                will(returnValue(asList(argumentTypes)));

                for (int i = 0; i < argumentTypes.length; i++) {
                    allowing(argumentTypes[i]).classUnderReflection();
                    will(returnValue(arguments[i]));
                }
            }
        });

    }

    protected final void whenTypeHasInterface(final Class<?> interfac3) {
        final ReflectedClass<?> interfaceType = context.mock(ReflectedClass.class, "interfaceType");
        context.checking(new Expectations() {
            {
                oneOf(type).interfaces();
                will(returnValue(list(interfaceType).$()));

                allowing(interfaceType).classUnderReflection();
                will(returnValue(interfac3));
            }
        });
    }

    protected final void whenTypeHasSuperclass(final Class<?> klass) {
        final ReflectedClass<?> superclassType = context.mock(ReflectedClass.class, "superclassType");
        context.checking(new Expectations() {
            {
                oneOf(type).superclasses();
                will(returnValue(list(superclassType).$()));

                allowing(superclassType).classUnderReflection();
                will(returnValue(klass));
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

    protected final void whenTypeHasNoInterface() {
        context.checking(new Expectations() {
            {
                oneOf(type).interfaces();
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
                oneOf(type).classUnderReflection();
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
