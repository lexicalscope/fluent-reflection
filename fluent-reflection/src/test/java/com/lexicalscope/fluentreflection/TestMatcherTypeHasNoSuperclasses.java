package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasNoSuperclasses;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherTypeHasNoSuperclasses extends AbstractTestReflectionMatcher<FluentAccess<?>> {
    class Base {

    }

    class Klass extends Base {

    }

    @Override protected FluentAccess<?> target() {
        return type(Base.class);
    }

    @Override protected FluentAccess<?> failingTarget() {
        return type(Klass.class);
    }

    @Override
    protected ReflectionMatcher<FluentAccess<?>> matcher() {
        return hasNoSuperclasses();
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("type that extends no superclasses");
    }
}
