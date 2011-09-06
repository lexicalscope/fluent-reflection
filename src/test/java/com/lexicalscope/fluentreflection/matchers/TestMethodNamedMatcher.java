package com.lexicalscope.fluentreflection.matchers;

import static com.lexicalscope.fluentreflection.matchers.ReflectionMatchers.methodNamed;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMethodNamedMatcher extends AbstractTestReflectionMatcher<ReflectedMethod> {
    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedMethod> matcher() {
        return methodNamed("abc");
    }

    @Override
    protected void setupMatchingCase() {
        whenMethodHasName("abc");
    }

    @Override
    protected void setupFailingCase() {
        whenMethodHasName("def");
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("method named \"abc\"");
    }
}
