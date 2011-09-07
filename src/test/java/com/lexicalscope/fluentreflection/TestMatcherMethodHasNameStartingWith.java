package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.methodHasNameStartingWith;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMatcherMethodHasNameStartingWith extends AbstractTestReflectionMatcher<ReflectedMethod> {
    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedMethod> matcher() {
        return methodHasNameStartingWith("abc");
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
        return equalTo("method starting with \"abc\"");
    }
}