package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.lexicalscope.fluentreflection.IllegalArgumentRuntimeException;

public class TestReflectedClassInstanceMethod {
    @Rule public final ExpectedException exception = ExpectedException.none();

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

    @Test public void simpleMethodCanBeCalled() {
        type(ClassWithInstanceMethods.class).method(hasName("simpleMethod")).call(instance);

        assertThat(instance.called, equalTo(true));
    }

    @Test public void callingMethodWithReturnValueReturnsValue() {
        final Integer result =
                (Integer) type(ClassWithInstanceMethods.class)
                        .method(hasName("methodWithReturnValue"))
                        .call(instance);

        assertThat(result, equalTo(42));
    }

    @Test public void canCallMethodWithOneArgumentIfMultipleMatches() {
        type(ClassWithInstanceMethods.class).method(hasName("methodWithOneArgument")).call(instance, "string");

        assertThat(instance.stringCalled || instance.objectCalled, equalTo(true));
    }

    @Test public void methodWithTwoArgumentsCanBeCalled() {
        type(ClassWithInstanceMethods.class).method(hasName("methodWithTwoArguments")).call(
                instance,
                "string",
                42);

        assertThat(instance.stringAndIntegerCalled, equalTo(true));
    }

    @Test public void methodWithTwoArgumentsHasCorrectArgumentCount() {
        assertThat(
                type(ClassWithInstanceMethods.class).method(hasName("methodWithTwoArguments")).argumentCount(),
                equalTo(2));
    }

    @SuppressWarnings("unchecked") @Test public void instanceMethodArgumentTypeIsCorrect() throws Exception {
        assertThat(
                type(ClassWithInstanceMethods.class).method(hasName("methodWithTwoArguments")).argumentTypes(),
                contains(
                        reflectingOn(String.class),
                        reflectingOn(Integer.class)));
    }

    @Test public void callingInstanceMethodWithoutInstanceFails() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("target instance must be specified");

        type(ClassWithInstanceMethods.class).method(hasName("simpleMethod")).call();
    }

    @Test public void callingInstanceMethodWithWrongInstanceType() {
        exception.expect(IllegalArgumentRuntimeException.class);
        exception.expectMessage(containsString("not an instance of declaring class"));

        type(ClassWithInstanceMethods.class).method(hasName("simpleMethod")).call(new Object());
    }
}
