package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectedTypeReflectingOn;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMatcherReflectedTypeReflectingOn extends AbstractTestReflectionMatcher<ReflectedClass<?>> {
    @Override
    protected ReflectedClass<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<ReflectedClass<?>> matcher() {
        return reflectedTypeReflectingOn(Object.class);
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
