package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasNoSuperclasses;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.FluentClass;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMatcherTypeHasNoSuperclasses extends AbstractTestReflectionMatcher<FluentClass<?>> {
    class Superclass {

    }

    @Override
    protected FluentClass<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<FluentClass<?>> matcher() {
        return hasNoSuperclasses();
    }

    @Override
    protected void setupMatchingCase() {
        whenTypeHasNoSuperclass();
    }

    @Override
    protected void setupFailingCase() {
        whenTypeHasSuperclass(Superclass.class);
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("type that extends no superclasses");
    }
}
