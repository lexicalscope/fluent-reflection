package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.method;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasName;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementNamed extends AbstractTestReflectionMatcherNoMocks<FluentMember> {
    interface Klass {
        void abc();
        void def();
    }

    @Override protected FluentMethod target() {
        return method(Klass.class, "abc");
    }

    @Override protected FluentMember failingTarget() {
        return method(Klass.class, "def");
    }

    @Override
    protected ReflectionMatcher<FluentMember> matcher() {
        return hasName("abc");
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("reflected element named \"abc\"");
    }
}
