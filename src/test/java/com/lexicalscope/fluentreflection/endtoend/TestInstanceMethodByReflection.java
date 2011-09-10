package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.Reflect.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.methodNamed;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestInstanceMethodByReflection {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public static class ClassWithInstanceMethods {
        private boolean called;
        private boolean objectCalled;
        private boolean stringCalled;
        private boolean stringAndIntegerCalled;

        public void simpleMethod() {
            called = true;
        }

        public int methodWithReturnValue() {
            return 42;
        }

        public void methodWithOneArgument(final String string) {
            stringCalled = true;
        }

        public void methodWithTwoArguments(final String string, final Integer integer) {
            stringAndIntegerCalled = true;
        }

        public void methodWithOneArgument(final Object object) {
            objectCalled = true;
        }
    }

    private final ClassWithInstanceMethods instance = new ClassWithInstanceMethods();

    @Test
    public void simpleMethodCanBeCalled() {
        type(ClassWithInstanceMethods.class).method(methodNamed("simpleMethod")).call(instance);

        assertThat(instance.called, equalTo(true));
    }

    @Test
    public void callingMethodWithReturnValueReturnsValue() {
        final Integer result =
                (Integer) type(ClassWithInstanceMethods.class)
                        .method(methodNamed("methodWithReturnValue"))
                        .call(instance);

        assertThat(result, equalTo(42));
    }

    @Test
    public void canCallMethodWithOneArgumentIfMultipleMatches() {
        type(ClassWithInstanceMethods.class).method(methodNamed("methodWithOneArgument")).call(instance, "string");

        assertThat(instance.stringCalled || instance.objectCalled, equalTo(true));
    }

    @Test
    public void methodWithTwoArgumentsCanBeCalled() {
        type(ClassWithInstanceMethods.class).method(methodNamed("methodWithTwoArguments")).call(instance, "string", 42);

        assertThat(instance.stringAndIntegerCalled, equalTo(true));
    }

    @Test
    public void callingInstanceMethodWithoutInstanceFails() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("target instance must be specified");

        type(ClassWithInstanceMethods.class).method(methodNamed("simpleMethod")).call();
    }

    @Test
    public void callingInstanceMethodWithWrongInstanceType() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("not an instance of declaring class");

        type(ClassWithInstanceMethods.class).method(methodNamed("simpleMethod")).call(new Object());
    }
}
