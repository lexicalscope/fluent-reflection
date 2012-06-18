package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasNameEndingWith;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementHasNameEndingWith extends AbstractTestReflectionMatcher<FluentMember> {
    @Override
    protected FluentMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<FluentMember> matcher() {
        return hasNameEndingWith("abc");
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
