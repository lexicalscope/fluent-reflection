package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.method;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasNameStartingWith;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementHasNameStartingWith extends AbstractTestReflectionMatcher<FluentMember> {
    interface Klass {
        void defabc();
        void abcdef();
    }

    @Override protected FluentMethod target() {
        return method(Klass.class, "abcdef");
    }

    @Override protected FluentMember failingTarget() {
        return method(Klass.class, "defabc");
    }

    @Override protected ReflectionMatcher<FluentMember> matcher() {
        return hasNameStartingWith("abc");
    }

    @Override protected Matcher<String> hasDescription() {
        return equalTo("callable starting with \"abc\"");
    }
}
