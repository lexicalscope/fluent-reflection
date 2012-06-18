package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasName;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementNamed extends AbstractTestReflectionMatcher<FluentMember> {
    @Override
    protected FluentMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<FluentMember> matcher() {
        return hasName("abc");
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
        return equalTo("reflected element named \"abc\"");
    }
}
