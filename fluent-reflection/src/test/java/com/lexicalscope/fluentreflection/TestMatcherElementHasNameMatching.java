package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.method;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasNameMatching;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementHasNameMatching extends AbstractTestReflectionMatcher<FluentMember> {
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

    @Override
    protected ReflectionMatcher<FluentMember> matcher() {
        return hasNameMatching(".+bc.+");
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable matching \".+bc.+\"");
    }
}
