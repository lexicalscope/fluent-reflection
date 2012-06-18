package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasType;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.jmock.Expectations;

public class TestMatcherElementHasVoidReturn extends AbstractTestReflectionMatcher<FluentMember> {
    @Override
    protected ReflectionMatcher<FluentMember> matcher() {
        return hasType((Class<?>) null);
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable with return type <void>");
    }

    @Override
    protected void setupFailingCase() {
        context.checking(new Expectations() {
            {
                oneOf(method).type();
                will(returnValue(type));

                oneOf(type).classUnderReflection();
                will(returnValue(Object.class));
            }
        });
    }

    @Override
    protected void setupMatchingCase() {
        context.checking(new Expectations() {
            {
                oneOf(method).type();
                will(returnValue(type));

                oneOf(type).classUnderReflection();
                will(returnValue(void.class));
            }
        });
    }

    @Override
    protected FluentMember target() {
        return method;
    }
}
