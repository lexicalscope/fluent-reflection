package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.anyReflectedType;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherAnyReflectedType extends AbstractTestReflectionMatcher<ReflectedClass<?>> {
    @Override
    protected ReflectionMatcher<ReflectedClass<?>> matcher() {
        return anyReflectedType();
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("any type");
    }

    @Override
    protected ReflectedClass<?> target() {
        return type;
    }

    @Override
    protected ReflectedClass<?> failingTarget() {
        return null;
    }
}
