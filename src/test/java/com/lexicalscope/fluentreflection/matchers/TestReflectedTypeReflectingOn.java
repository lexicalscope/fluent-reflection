package com.lexicalscope.fluentreflection.matchers;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedType;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestReflectedTypeReflectingOn extends AbstractTestReflectionMatcher<ReflectedType<?>> {
    @Override
    protected ReflectedType<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<ReflectedType<?>> matcher() {
        return new ReflectedTypeReflectingOn(Object.class);
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
        return equalTo("reflecting on type <class java.lang.Object>");
    }
}
