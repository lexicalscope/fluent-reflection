package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.isAnInterface;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.FluentClass;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMatcherTypeIsInterface extends AbstractTestReflectionMatcher<FluentClass<?>> {
    @Override
    protected FluentClass<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<FluentClass<?>> matcher() {
        return isAnInterface();
    }

    @Override
    protected void setupMatchingCase() {
        whenTypeIsInterface();
    }

    @Override
    protected void setupFailingCase() {
        whenTypeIsNotInterface();
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("type that is an interface");
    }
}
