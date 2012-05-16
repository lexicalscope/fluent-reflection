package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasReturnType;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.jmock.Expectations;

public class TestMatcherCallableHasVoidReturn extends AbstractTestReflectionMatcher<ReflectedCallable> {
    @Override
    protected ReflectionMatcher<ReflectedCallable> matcher() {
        return hasReturnType((Class<?>) null);
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable with return type <void>");
    }

    @Override
    protected void setupFailingCase() {
        context.checking(new Expectations() {
            {
                oneOf(method).returnType();
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
                oneOf(method).returnType();
                will(returnValue(type));

                oneOf(type).classUnderReflection();
                will(returnValue(void.class));
            }
        });
    }

    @Override
    protected ReflectedCallable target() {
        return method;
    }
}
