package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasNameContaining;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementHasNameContaining extends AbstractTestReflectionMatcher<FluentMember> {
    @Override
    protected FluentMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<FluentMember> matcher() {
        return hasNameContaining("abc");
    }

    @Override
    protected void setupMatchingCase() {
        whenMethodHasName("defabc");
    }

    @Override
    protected void setupFailingCase() {
        whenMethodHasName("pqrxyz");
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable containing \"abc\"");
    }
}
