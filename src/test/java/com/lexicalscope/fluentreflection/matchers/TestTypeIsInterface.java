package com.lexicalscope.fluentreflection.matchers;

import static com.lexicalscope.fluentreflection.matchers.ReflectionMatchers.typeIsInterface;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedType;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestTypeIsInterface extends AbstractTestReflectionMatcher<ReflectedType<?>> {
    @Override
    protected ReflectedType<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<ReflectedType<?>> matcher() {
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
