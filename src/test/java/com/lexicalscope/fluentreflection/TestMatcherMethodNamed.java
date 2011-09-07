package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.methodNamed;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMatcherMethodNamed extends AbstractTestReflectionMatcher<ReflectedMethod> {
    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedMethod> matcher() {
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
        return equalTo("method named \"abc\"");
    }
}