package com.lexicalscope.fluentreflection.matchers;

import static com.lexicalscope.fluentreflection.matchers.ReflectionMatchers.typeHasInterface;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedType;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestTypeHasInterfaces extends AbstractTestReflectionMatcher<ReflectedType<?>> {
    @Override
    protected ReflectedType<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<ReflectedType<?>> matcher() {
        return typeHasInterface(Object.class);
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
