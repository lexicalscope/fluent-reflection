package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasNoInterfaces;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherTypeHasNoInterfaces extends AbstractTestReflectionMatcher<FluentAccess<?>> {
    interface Interfa3c {

    }

    interface Klass extends Interfa3c {

    }

    @Override protected FluentAccess<?> target() {
        return type(Object.class);
    }

    @Override protected FluentAccess<?> failingTarget() {
        return type(Klass.class);
    }

    @Override protected ReflectionMatcher<FluentAccess<?>> matcher() {
        return hasNoInterfaces();
    }

    @Override protected Matcher<String> hasDescription() {
        return equalTo("type that implements no interfaces");
    }
}
