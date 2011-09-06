package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.typeHasNoSuperclasses;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedType;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMatcherTypeHasNoSuperclasses extends AbstractTestReflectionMatcher<ReflectedType<?>> {
    class Superclass {

    }

    @Override
    protected ReflectedType<?> target() {
        return type;
    }

    @Override
    protected ReflectionMatcher<ReflectedType<?>> matcher() {
        return typeHasNoSuperclasses();
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
