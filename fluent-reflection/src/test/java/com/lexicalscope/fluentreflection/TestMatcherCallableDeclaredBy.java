package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.declaredBy;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherCallableDeclaredBy extends AbstractTestReflectionMatcher<ReflectedMember> {
    interface DeclaringInterface {

    }

    interface NonDeclaringInterface {

    }

    @Override protected ReflectedMethod target() {
        return method;
    }

    @Override protected ReflectionMatcher<ReflectedMember> matcher() {
        return declaredBy(DeclaringInterface.class);
    }

    @Override protected void setupMatchingCase() {
        whenMethodDeclaredBy(DeclaringInterface.class);
    }

    @Override protected void setupFailingCase() {
        whenMethodDeclaredBy(NonDeclaringInterface.class);
    }

    @Override protected Matcher<String> hasDescription() {
        return equalTo("callable declared by type <" + DeclaringInterface.class + ">");
    }
}
