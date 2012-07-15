package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.method;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasArgumentCount;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherArgumentCount extends AbstractTestReflectionMatcherNoMocks<FluentMember> {
    interface Klass {
        void threeArgs(Object one, Object two, Object three);
        void fiveArgs(Object one, Object two, Object three, Object four, Object five);
    }

    @Override protected ReflectionMatcher<FluentMember> matcher() {
        return hasArgumentCount(3);
    }

    @Override protected Matcher<String> hasDescription() {
        return equalTo("callable with <3> arguments");
    }

    @Override protected FluentMember target() {
        return method(Klass.class, "threeArgs");
    }

    @Override protected FluentMember failingTarget() {
        return method(Klass.class, "fiveArgs");
    }
}
