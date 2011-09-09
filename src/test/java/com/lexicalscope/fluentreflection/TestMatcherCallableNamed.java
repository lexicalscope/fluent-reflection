package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.methodNamed;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherCallableNamed extends AbstractTestReflectionMatcher<ReflectedCallable> {
    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedCallable> matcher() {
        return methodNamed("abc");
    }

    @Override
    protected void setupMatchingCase() {
        whenMethodHasName("abc");
    }

    @Override
    protected void setupFailingCase() {
        whenMethodHasName("def");
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable named \"abc\"");
    }
}
