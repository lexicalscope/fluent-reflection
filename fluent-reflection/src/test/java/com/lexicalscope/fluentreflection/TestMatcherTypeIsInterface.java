package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.typeIsInterface;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMatcherTypeIsInterface extends AbstractTestReflectionMatcher<ReflectedClass<?>> {
    @Override
    protected ReflectedClass<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<ReflectedClass<?>> matcher() {
        return typeIsInterface();
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
