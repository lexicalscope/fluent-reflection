package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasArguments;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementWithArguments extends AbstractTestReflectionMatcher<ReflectedMember> {
    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedMember> matcher() {
        return hasArguments(String.class);
    }

    @Override
    protected void setupMatchingCase() {
        whenMethodHasArguments(String.class);
    }

    @Override
    protected void setupFailingCase() {
        whenMethodHasArguments(String.class, String.class);
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable with arguments (type assignable from <class java.lang.String>)");
    }
}
