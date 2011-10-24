package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.callableHasArguments;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherCallableWithArguments extends AbstractTestReflectionMatcher<ReflectedCallable> {
    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedCallable> matcher() {
        return callableHasArguments(String.class);
    }

    @Override
    protected void setupMatchingCase() {
        whenMethodHasArguments(String.class);
    }

    @Override
    protected void setupFailingCase() {
        whenMethodHasArguments(String.class, String.class);
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable with arguments (type assignable from <class java.lang.String>)");
    }
}
