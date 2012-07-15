package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectingOn;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherReflectedTypeReflectingOn extends AbstractTestReflectionMatcher<FluentAccess<?>> {
    interface Klass {

    }

    @Override protected FluentAccess<?> target() {
        return type(Object.class);
    }

    @Override protected FluentAccess<?> failingTarget() {
        return type(String.class);
    }

    @Override protected ReflectionMatcher<FluentAccess<?>> matcher() {
        return reflectingOn(Object.class);
    }

    @Override protected Matcher<String> hasDescription() {
        return equalTo("type <class java.lang.Object>");
    }
}
