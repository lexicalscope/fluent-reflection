package com.lexicalscope.fluentreflection;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.google.inject.TypeLiteral;

@SuppressWarnings("deprecation") class JUnitRuleMockery extends JUnit4Mockery implements MethodRule {
    @SuppressWarnings("unchecked") public <T> T mock(final TypeLiteral<T> typeToMock) {
        return (T) super.mock(typeToMock.getRawType());
    }

    @Override public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
        return new Statement() {
            @Override public void evaluate() throws Throwable {
                base.evaluate();
                assertIsSatisfied();
            }
        };
    }

    public static <T> Action returnList(final T t) {
        final List<T> list = new ArrayList<T>();
        list.add(t);
        return new Action() {
            @Override public void describeTo(final Description description) {
                description.appendText("return ").appendValue(list);
            }

            @Override public Object invoke(final Invocation arg0) throws Throwable {
                return unmodifiableList(list);
            }
        };
    }
}