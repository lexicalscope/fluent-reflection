package com.lexicalscope.fluentreflection.matchers;

import static com.lexicalscope.fluentreflection.matchers.ReflectionMatchers.methodHasNameContaining;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMethodHasNameContaining extends AbstractTestReflectionMatcher<ReflectedMethod> {
    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedMethod> matcher() {
        return methodHasNameContaining("abc");
    }

    @Override
    protected void setupMatchingCase() {
        whenMethodHasName("defabc");
    }

    @Override
    protected void setupFailingCase() {
        whenMethodHasName("pqrxyz");
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("method containing \"abc\"");
    }
}
