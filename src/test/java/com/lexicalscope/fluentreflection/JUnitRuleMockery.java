package com.lexicalscope.fluentreflection;

import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

class JUnitRuleMockery extends JUnit4Mockery implements MethodRule {
    @Override
    public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                base.evaluate();
                assertIsSatisfied();
            }
        };
    }
}