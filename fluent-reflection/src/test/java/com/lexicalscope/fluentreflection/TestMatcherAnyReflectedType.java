package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.anyReflectedType;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherAnyReflectedType extends AbstractTestReflectionMatcher<FluentClass<?>> {
    @Override
    protected ReflectionMatcher<FluentClass<?>> matcher() {
        return anyReflectedType();
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("any type");
    }

    @Override
    protected FluentClass<?> target() {
        return type;
    }

    @Override
    protected FluentClass<?> failingTarget() {
        return null;
    }
}
