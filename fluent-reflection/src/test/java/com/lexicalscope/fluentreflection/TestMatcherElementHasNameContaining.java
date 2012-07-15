package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.method;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasNameContaining;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementHasNameContaining extends AbstractTestReflectionMatcherNoMocks<FluentMember> {
    interface Klass {
        void defabc();
        void pqrxyz();
    }

    @Override protected FluentMethod target() {
        return method(Klass.class, "defabc");
    }

    @Override protected FluentMember failingTarget() {
        return method(Klass.class, "pqrxyz");
    }

    @Override protected ReflectionMatcher<FluentMember> matcher() {
        return hasNameContaining("abc");
    }

    @Override protected Matcher<String> hasDescription() {
        return equalTo("callable containing \"abc\"");
    }
}
