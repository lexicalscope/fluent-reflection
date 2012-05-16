package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.hasName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;

public class TestReflectedClassStaticMethods {
    public static class ClassWithStaticMethods {
        private static boolean called;
        private static boolean objectCalled;
        private static boolean stringCalled;
        private static boolean stringAndIntegerCalled;

        public static void simpleStaticMethod() {
            called = true;
        }

        public static int staticMethodWithReturnValue() {
            return 42;
        }

        public static void staticMethodWithOneArgument(final String string) {
            stringCalled = true;
        }

        public static void staticMethodWithTwoArguments(final String string, final Integer integer) {
            stringAndIntegerCalled = true;
        }

        public static void staticMethodWithOneArgument(final Object object) {
            objectCalled = true;
        }
    }

    @Before
    public void resetStaticFixture() {
        ClassWithStaticMethods.called = false;
        ClassWithStaticMethods.objectCalled = false;
        ClassWithStaticMethods.stringCalled = false;
        ClassWithStaticMethods.stringAndIntegerCalled = false;
    }

    @Test
    public void callSimpleStaticMethod() {
        type(ClassWithStaticMethods.class).staticMethod(hasName("simpleStaticMethod")).call();

        assertThat(
                ClassWithStaticMethods.called,
                equalTo(true));
    }

    @Test
    public void callStaticMethodWithReturnValue() {
        final Integer result =
                (Integer) type(ClassWithStaticMethods.class)
                        .staticMethod(hasName("staticMethodWithReturnValue"))
                        .call();

        assertThat(result, equalTo(42));
    }

    @Test
    public void callStaticMethodWithOneArgumentIfMultipleMatches() {
        type(ClassWithStaticMethods.class).staticMethod(hasName("staticMethodWithOneArgument")).call("string");
        assertThat(ClassWithStaticMethods.stringCalled || ClassWithStaticMethods.objectCalled,
                equalTo(true));
    }

    @Test
    public void callstaticMethodWithTwoArguments() {
        type(ClassWithStaticMethods.class).staticMethod(hasName("staticMethodWithTwoArguments")).call("string", 42);
        assertThat(ClassWithStaticMethods.stringAndIntegerCalled,
                equalTo(true));
    }

    @Test
    public void methodWithTwoArgumentsHasCorrectArgumentCount() {
        assertThat(
                type(ClassWithStaticMethods.class).method(hasName("staticMethodWithTwoArguments")).argumentCount(),
                equalTo(2));
    }
}
