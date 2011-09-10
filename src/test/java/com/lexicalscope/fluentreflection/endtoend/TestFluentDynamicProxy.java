package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.Reflect.dynamicProxy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anything;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

import com.lexicalscope.fluentreflection.Implementing;

public class TestFluentDynamicProxy {
    interface TwoMethods {
        void methodA();

        void methodB();
    }

    private final List<String> called = new ArrayList<String>();

    @Test
    public void proxyEveryMethod() throws Exception {

        final TwoMethods dynamicProxy = dynamicProxy(new Implementing<TwoMethods>() {
            {
                matching(anything()).execute(new Object() {
                    class Body {
                        {
                            called.add(methodName());
                        }
                    }
                });
            }
        });

        assertThat(called, Matchers.<String>empty());

        dynamicProxy.methodA();

        assertThat(called, Matchers.contains("methodA"));
    }

    @Test
    @Ignore("needs a better error message and fail fast")
    public void cannotCloseOverMethodVariables() throws Exception {
        final List<String> closedVariable = new ArrayList<String>();
        final TwoMethods dynamicProxy = dynamicProxy(new Implementing<TwoMethods>() {
            {
                matching(Matchers.anything()).execute(new Object() {
                    class Body {
                        {
                            closedVariable.add("never executed");
                        }
                    }
                });
            }
        });
        dynamicProxy.methodA();
    }
}
