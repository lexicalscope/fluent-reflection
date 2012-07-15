package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.anyFluentType;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherAnyFluentType extends AbstractTestReflectionMatcher<FluentAccess<?>> {
    @Override
    protected FluentClass<?> failingTarget() {
        return null;
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("any type");
    }

    @Override
    protected ReflectionMatcher<FluentAccess<?>> matcher() {
        return anyFluentType();
    }

    @Override
    protected FluentClass<?> target() {
        return type;
    }
}
