package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasNoInterfaces;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.FluentClass;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMatcherTypeHasNoInterfaces extends AbstractTestReflectionMatcher<FluentClass<?>> {
    @Override
    protected FluentClass<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<FluentClass<?>> matcher() {
        return hasNoInterfaces();
    }

    @Override
    protected void setupMatchingCase() {
        whenTypeHasNoInterface();
    }

    @Override
    protected void setupFailingCase() {
        whenTypeHasInterface(Object.class);
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("type that implements no interfaces");
    }
}
