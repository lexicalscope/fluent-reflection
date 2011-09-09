package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.methodHasNameContaining;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherCallableHasNameContaining extends AbstractTestReflectionMatcher<ReflectedCallable> {
    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedCallable> matcher() {
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
        return equalTo("callable containing \"abc\"");
    }
}
