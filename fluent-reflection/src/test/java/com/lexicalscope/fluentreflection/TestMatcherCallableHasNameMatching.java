package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.callableHasNameMatching;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherCallableHasNameMatching extends AbstractTestReflectionMatcher<ReflectedCallable> {
    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedCallable> matcher() {
        return callableHasNameMatching(".+bc.+");
    }

    @Override
    protected void setupMatchingCase() {
        whenMethodHasName("abcdef");
    }

    @Override
    protected void setupFailingCase() {
        whenMethodHasName("defabc");
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable matching \".+bc.+\"");
    }
}
