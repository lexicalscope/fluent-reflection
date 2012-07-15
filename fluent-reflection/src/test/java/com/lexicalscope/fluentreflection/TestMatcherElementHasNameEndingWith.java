package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.method;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasNameEndingWith;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementHasNameEndingWith extends AbstractTestReflectionMatcherNoMocks<FluentMember> {
    interface Klass {
        void defabc();
        void abcdef();
    }

    @Override protected FluentMethod target() {
        return method(Klass.class, "defabc");
    }

    @Override protected FluentMember failingTarget() {
        return method(Klass.class, "abcdef");
    }

    @Override protected ReflectionMatcher<FluentMember> matcher() {
        return hasNameEndingWith("abc");
    }

    @Override protected Matcher<String> hasDescription() {
        return equalTo("callable ending with \"abc\"");
    }
}
