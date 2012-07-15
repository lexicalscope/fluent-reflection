package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectingOn;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherReflectedTypeReflectingOn extends AbstractTestReflectionMatcher<FluentAccess<?>> {
    @Override
    protected FluentClass<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<FluentAccess<?>> matcher() {
        return reflectingOn(Object.class);
    }

    @Override
    protected void setupMatchingCase() {
        whenType(Object.class);
    }

    @Override
    protected void setupFailingCase() {
        whenType(String.class);
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("type <class java.lang.Object>");
    }
}
