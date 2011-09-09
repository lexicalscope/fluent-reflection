package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.methodDeclaredBy;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherCallableDeclaredBy extends AbstractTestReflectionMatcher<ReflectedCallable> {
    interface DeclaringInterface {

    }

    interface NonDeclaringInterface {

    }

    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedCallable> matcher() {
        return methodDeclaredBy(DeclaringInterface.class);
    }

    @Override
    protected void setupMatchingCase() {
        whenMethodDeclaredBy(DeclaringInterface.class);
    }

    @Override
    protected void setupFailingCase() {
        whenMethodDeclaredBy(NonDeclaringInterface.class);
    }

    @Override
    protected Matcher<String> hasDescription() {
        return equalTo("callable declared by <" + DeclaringInterface.class + ">");
    }
}
