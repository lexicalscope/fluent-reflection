package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class TestRawConstructionByReflection {
    public static class ClassWithDefaultConstructor {
        private final boolean called;

        public ClassWithDefaultConstructor() {
            called = true;
        }
    }

    public static class ClassWithTwoConstructors {
        private boolean stringCalled;
        private boolean stringAndIntegerCalled;

        public ClassWithTwoConstructors(final String string) {
            stringCalled = true;
        }

        public ClassWithTwoConstructors(final String string, final Integer integer) {
            stringAndIntegerCalled = true;
        }
    }

    public static class ClassWithAmbigiousConstructors {
        private final boolean called;

        public ClassWithAmbigiousConstructors(final Object object) {
            called = true;
        }

        public ClassWithAmbigiousConstructors(final String string) {
            called = true;
        }
    }

    @Test
    public void constructUsingDefaultConstructor() {
        assertThat(type(ClassWithDefaultConstructor.class).construct().value().called, equalTo(true));
    }

    @Test
    public void constructUsingOneArgumentConstructor() {
        assertThat(type(ClassWithTwoConstructors.class).construct("string").value().stringCalled, equalTo(true));
    }

    @Test
    public void constructUsingOneArgumentWithNullValueConstructor() {
        assertThat(type(ClassWithTwoConstructors.class).construct((Object) null).value().stringCalled, equalTo(true));
    }

    @Test
    public void constructUsingTwoArgumentConstructor() {
        assertThat(type(ClassWithTwoConstructors.class).construct("string", 13).value().stringAndIntegerCalled, equalTo(true));
    }

    @Test
    public void constructUsingTwoArgumentWithOneNullValueConstructor() {
        assertThat(type(ClassWithTwoConstructors.class).construct("string", null).value().stringAndIntegerCalled, equalTo(true));
    }

    @Test
    public void constructUsingTwoArgumentWithTwoNullValueConstructor() {
        assertThat(type(ClassWithTwoConstructors.class).construct(null, null).value().stringAndIntegerCalled, equalTo(true));
    }

    @Test
    public void constructUsingAnyConstructorIfMultipleMatchers() {
        assertThat(type(ClassWithAmbigiousConstructors.class).construct("string").value().called, equalTo(true));
    }
}
