package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.method;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasType;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementHasVoidReturn extends AbstractTestReflectionMatcher<FluentMember> {
    interface Klass {
        void voidMethod();
        String stringMethod();
    }

    @Override protected FluentMethod target() {
        return method(Klass.class, "voidMethod");
    }

    @Override protected FluentMember failingTarget() {
        return method(Klass.class, "stringMethod");
    }

    @Override protected ReflectionMatcher<FluentMember> matcher() {
        return hasType((Class<?>) null);
    }

    @Override protected Matcher<String> hasDescription() {
        return equalTo("callable with return type <void>");
    }
}
