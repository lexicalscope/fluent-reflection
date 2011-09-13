package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.callableHasReturnType;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.jmock.Expectations;

public class TestMatcherCallableHasVoidReturn extends AbstractTestReflectionMatcher<ReflectedCallable> {
    @Override
    protected ReflectionMatcher<ReflectedCallable> matcher() {
        return callableHasReturnType((Class<?>) null);
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable with no return type");
    }

    @Override
    protected void setupFailingCase() {
        context.checking(new Expectations() {
            {
                oneOf(method).returnType();
                will(returnValue(type));
            }
        });
    }

    @Override
    protected void setupMatchingCase() {
        context.checking(new Expectations() {
            {
                oneOf(method).returnType();
                will(returnValue(null));
            }
        });
    }

    @Override
    protected ReflectedCallable target() {
        return method;
    }
}
