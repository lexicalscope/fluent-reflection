package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.Reflect.dynamicProxy;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.methodNamed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lexicalscope.fluentreflection.Implementing;
import com.lexicalscope.fluentreflection.MethodBody;

public class TestFluentDynamicProxy {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    static class MyException extends Exception {
        private static final long serialVersionUID = -4179362193684542734L;
    }

    interface ThrowingMethod {
        void method() throws MyException;
    }

    interface TwoMethods {
        void methodA();

        void methodB();
    }

    interface QueryMethod {
        int methodA();

        int methodB();
    }

    @Test
    public void canProxyMultipleMethods() throws Exception {
        final List<String> called = new ArrayList<String>();

        final TwoMethods dynamicProxy = dynamicProxy(new Implementing<TwoMethods>() {
            {
                matching(anything()).execute(new MethodBody() {
                    @Override
                    public void body() {
                        called.add(methodName());
                    }
                });
            }
        });

        assertThat(called, Matchers.<String>empty());

        dynamicProxy.methodA();

        assertThat(called, contains("methodA"));

        dynamicProxy.methodB();

        assertThat(called, contains("methodA", "methodB"));
    }

    @Test
    public void canReturnValueFromProxiedMethod() throws Exception {

        final QueryMethod dynamicProxy = dynamicProxy(new Implementing<QueryMethod>() {
            {
                matching(anything()).execute(new MethodBody() {
                    @Override
                    public void body() {
                        returnValue(42);
                    }
                });
            }
        });

        assertThat(dynamicProxy.methodA(), equalTo(42));
        assertThat(dynamicProxy.methodB(), equalTo(42));
    }

    @Test
    public void canReturnDifferentValuesFromEachProxiedMethod() throws Exception {

        final QueryMethod dynamicProxy = dynamicProxy(new Implementing<QueryMethod>() {
            {
                matching(methodNamed("methodA")).execute(new MethodBody() {
                    @Override
                    public void body() {
                        returnValue(42);
                    }
                });

                matching(methodNamed("methodB")).execute(new MethodBody() {
                    @Override
                    public void body() {
                        returnValue(24);
                    }
                });
            }
        });

        assertThat(dynamicProxy.methodA(), equalTo(42));
        assertThat(dynamicProxy.methodB(), equalTo(24));
    }

    @Test
    public void canDefineDefaultImplementationForUnmatchedMethods() throws Exception {

        final QueryMethod dynamicProxy = dynamicProxy(new Implementing<QueryMethod>() {
            {
                matching(methodNamed("methodA")).execute(new MethodBody() {
                    @Override
                    public void body() {
                        returnValue(42);
                    }
                });

                matching(anything()).execute(new MethodBody() {
                    @Override
                    public void body() {
                        returnValue(24);
                    }
                });
            }
        });

        assertThat(dynamicProxy.methodA(), equalTo(42));
        assertThat(dynamicProxy.methodB(), equalTo(24));
    }

    @Test
    public void canThrowException() throws Exception {

        final ThrowingMethod dynamicProxy = dynamicProxy(new Implementing<ThrowingMethod>() {
            {
                matching(anything()).execute(new MethodBody() {
                    @Override
                    public void body() throws MyException {
                        throw new MyException();
                    }
                });
            }
        });

        exception.expect(MyException.class);
        dynamicProxy.method();
    }
}
