package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.methodWithArguments;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMethodWithArgumentsMatcher extends AbstractTestReflectionMatcher<ReflectedMethod> {
    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedMethod> matcher() {
        return methodWithArguments(String.class);
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
        return equalTo("method with arguments [<class java.lang.String>]");
    }
}
