package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasInterface;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMatcherTypeHasInterfaces extends AbstractTestReflectionMatcher<ReflectedClass<?>> {
    @Override
    protected ReflectedClass<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<ReflectedClass<?>> matcher() {
        return hasInterface(Object.class);
    }

    @Override
    protected void setupMatchingCase() {
        whenTypeHasInterface(Object.class);
    }

    @Override
    protected void setupFailingCase() {
        whenTypeHasNoInterface();
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("type that implements interface <class java.lang.Object>");
    }
}
