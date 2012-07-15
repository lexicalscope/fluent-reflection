package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.method;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasArguments;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementWithArguments extends AbstractTestReflectionMatcherNoMocks<FluentMember> {
    interface Klass {
        void matchingMethod(String argument);
        void failingMethod(String argument0, String argument1);
    }

    @Override
    protected FluentMethod target() {
        return method(Klass.class, "matchingMethod");
    }

    @Override protected FluentMember failingTarget() {
        return method(Klass.class, "failingMethod");
    }

    @Override
    protected ReflectionMatcher<FluentMember> matcher() {
        return hasArguments(String.class);
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable with arguments (type assignable from <class java.lang.String>)");
    }
}
