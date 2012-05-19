package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasNameStartingWith;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementHasNameStartingWith extends AbstractTestReflectionMatcher<ReflectedMember> {
    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedMember> matcher() {
        return hasNameStartingWith("abc");
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
        return equalTo("callable starting with \"abc\"");
    }
}
