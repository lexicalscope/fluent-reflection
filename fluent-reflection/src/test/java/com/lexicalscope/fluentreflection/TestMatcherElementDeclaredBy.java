package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.method;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.declaredBy;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

public class TestMatcherElementDeclaredBy extends AbstractTestReflectionMatcher<FluentMember> {
    interface NonDeclaringInterface {
        void basemethod();
    }

    interface DeclaringInterface extends NonDeclaringInterface{
        void submethod();
    }

    @Override protected FluentMethod target() {
        return method(DeclaringInterface.class, "submethod");
    }

    @Override protected FluentMember failingTarget() {
        return method(DeclaringInterface.class, "basemethod");
    }

    @Override protected ReflectionMatcher<FluentMember> matcher() {
        return declaredBy(DeclaringInterface.class);
    }

    @Override protected Matcher<String> hasDescription() {
        return equalTo("callable declared by type <" + DeclaringInterface.class + ">");
    }
}
