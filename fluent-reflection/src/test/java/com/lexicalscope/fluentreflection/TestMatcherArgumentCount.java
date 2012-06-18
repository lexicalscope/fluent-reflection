package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasArgumentCount;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.jmock.Expectations;

public class TestMatcherArgumentCount extends AbstractTestReflectionMatcher<FluentMember> {
    @Override
    protected ReflectionMatcher<FluentMember> matcher() {
        return hasArgumentCount(3);
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable with <3> arguments");
    }

    @Override
    protected void setupFailingCase() {
        context.checking(new Expectations() {
            {
                oneOf(callable).argCount();
                will(returnValue(5));
            }
        });
    }

    @Override
    protected void setupMatchingCase() {
        context.checking(new Expectations() {
            {
                oneOf(callable).argCount();
                will(returnValue(3));
            }
        });
    }

    @Override
    protected FluentMember target() {
        return callable;
    }
}
