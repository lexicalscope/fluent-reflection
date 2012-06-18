package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.declaredBy;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementDeclaredBy extends AbstractTestReflectionMatcher<FluentMember> {
    interface DeclaringInterface {

    }

    interface NonDeclaringInterface {

    }

    @Override protected FluentMethod target() {
        return method;
    }

    @Override protected ReflectionMatcher<FluentMember> matcher() {
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
