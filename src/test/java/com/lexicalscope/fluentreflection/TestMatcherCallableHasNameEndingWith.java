package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.callableHasNameEndingWith;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherCallableHasNameEndingWith extends AbstractTestReflectionMatcher<ReflectedCallable> {
    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedCallable> matcher() {
        return callableHasNameEndingWith("abc");
    }

    @Override
    protected void setupMatchingCase() {
        whenMethodHasName("defabc");
    }

    @Override
    protected void setupFailingCase() {
        whenMethodHasName("abcdef");
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable ending with \"abc\"");
    }
}
