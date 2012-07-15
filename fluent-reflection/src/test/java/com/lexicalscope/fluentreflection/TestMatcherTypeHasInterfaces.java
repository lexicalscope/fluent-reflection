package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasInterface;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherTypeHasInterfaces extends AbstractTestReflectionMatcher<FluentAccess<?>> {
    interface Interfa3c {

    }

    interface Klass extends Interfa3c {

    }

    @Override protected FluentAccess<?> target() {
        return type(Klass.class);
    }

    @Override protected FluentAccess<?> failingTarget() {
        return type(Object.class);
    }

    @Override
    protected ReflectionMatcher<FluentAccess<?>> matcher() {
        return hasInterface(Interfa3c.class);
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("type that implements interface <interface com.lexicalscope.fluentreflection.TestMatcherTypeHasInterfaces$Interfa3c>");
    }
}
