package com.lexicalscope.fluentreflection.dynamicproxy.endtoend;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.methodNamed;
import static com.lexicalscope.fluentreflection.dynamicproxy.FluentProxy.dynamicProxy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lexicalscope.fluentreflection.dynamicproxy.FluentProxy;
import com.lexicalscope.fluentreflection.dynamicproxy.Implementing;
import com.lexicalscope.fluentreflection.dynamicproxy.MethodBody;
import com.lexicalscope.fluentreflection.dynamicproxy.QueryMethod;

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

    interface TwoQueryMethod {
        int methodA();

        int methodB();
    }

    interface MethodWithArgument {
        int method(int argument);
    }

    @Test
    public void canProxyMultipleMethods() throws Exception {
        final List<String> called = new ArrayList<String>();

        final TwoMethods dynamicProxy = FluentProxy.dynamicProxy(new Implementing<TwoMethods>() {
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

        final TwoQueryMethod dynamicProxy = dynamicProxy(new Implementing<TwoQueryMethod>() {
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

        final TwoQueryMethod dynamicProxy = dynamicProxy(new Implementing<TwoQueryMethod>() {
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

        final TwoQueryMethod dynamicProxy = dynamicProxy(new Implementing<TwoQueryMethod>() {
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

    @Test
    public void canGetMethodArguments() throws Exception {
        final MethodWithArgument dynamicProxy = dynamicProxy(new Implementing<MethodWithArgument>() {
            {
                matching(anything()).execute(new MethodBody() {
                    @Override
                    public void body() {
                        returnValue(args()[0]);
                    }
                });
            }
        });

        assertThat(dynamicProxy.method(42), equalTo(42));
    }

    @Test
    public void canGetBindUsingMethodArguments() throws Exception {
        final MethodWithArgument dynamicProxy = dynamicProxy(new Implementing<MethodWithArgument>() {
            {
                matchingSignature(new QueryMethod() {
                    @SuppressWarnings("unused")
                    public int body(final int agument)
                    {
                        return 42;
                    }
                });
            }
        });

        assertThat(dynamicProxy.method(42), equalTo(42));
    }
}
