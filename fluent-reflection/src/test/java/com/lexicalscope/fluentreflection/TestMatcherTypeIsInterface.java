package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.isAnInterface;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherTypeIsInterface extends AbstractTestReflectionMatcher<FluentAccess<?>> {
    interface Interfa3c {

    }

    class Klass {

    }

    @Override protected FluentAccess<?> target() {
        return type(Interfa3c.class);
    }

    @Override protected FluentAccess<?> failingTarget() {
        return type(Klass.class);
    }

    @Override protected ReflectionMatcher<FluentAccess<?>> matcher() {
        return isAnInterface();
    }

    @Override protected Matcher<String> hasDescription() {
        return equalTo("type that is an interface");
    }
}
