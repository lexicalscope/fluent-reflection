package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.typeHasNoInterfaces;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedType;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMatcherTypeHasNoInterfaces extends AbstractTestReflectionMatcher<ReflectedType<?>> {
    @Override
    protected ReflectedType<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<ReflectedType<?>> matcher() {
        return typeHasNoInterfaces();
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
