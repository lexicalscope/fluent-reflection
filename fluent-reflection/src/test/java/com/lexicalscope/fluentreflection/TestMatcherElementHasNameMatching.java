package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasNameMatching;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementHasNameMatching extends AbstractTestReflectionMatcher<FluentMember> {
    @Override
    protected FluentMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<FluentMember> matcher() {
        return hasNameMatching(".+bc.+");
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
