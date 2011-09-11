package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.typeHasNoInterfaces;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedClass;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMatcherTypeHasNoInterfaces extends AbstractTestReflectionMatcher<ReflectedClass<?>> {
    @Override
    protected ReflectedClass<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<ReflectedClass<?>> matcher() {
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
