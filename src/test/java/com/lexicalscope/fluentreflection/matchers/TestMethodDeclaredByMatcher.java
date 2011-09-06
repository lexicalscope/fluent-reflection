package com.lexicalscope.fluentreflection.matchers;

import static com.lexicalscope.fluentreflection.matchers.ReflectionMatchers.methodDeclaredBy;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

public class TestMethodDeclaredByMatcher extends AbstractTestReflectionMatcher<ReflectedMethod> {
    interface DeclaringInterface {

    }

    interface NonDeclaringInterface {

    }

    @Override
    protected ReflectedMethod target() {
        return method;
    }

    @Override
    protected ReflectionMatcher<ReflectedMethod> matcher() {
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
        return equalTo("method declared by <" + DeclaringInterface.class + ">");
    }
}
