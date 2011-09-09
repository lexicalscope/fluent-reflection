package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.Reflect.type;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class TestConstructionByReflection {
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

    @Test
    public void constructUsingDefaultConstructor() {
        assertThat(type(ClassWithDefaultConstructor.class).construct().called, equalTo(true));
    }

    @Test
    public void constructUsingOneArgumentConstructor() {
        assertThat(type(ClassWithTwoConstructors.class).construct("string").stringCalled, equalTo(true));
    }

    @Test
    public void constructUsingOneArgumentWithNullValueConstructor() {
        assertThat(type(ClassWithTwoConstructors.class).construct((Object) null).stringCalled, equalTo(true));
    }

    @Test
    public void constructUsingTwoArgumentConstructor() {
        assertThat(type(ClassWithTwoConstructors.class).construct("string", 13).stringAndIntegerCalled, equalTo(true));
    }

    @Test
    public void constructUsingTwoArgumentWithOneNullValueConstructor() {
        assertThat(type(ClassWithTwoConstructors.class).construct("string", null).stringAndIntegerCalled, equalTo(true));
    }

    @Test
    public void constructUsingTwoArgumentWithTwoNullValueConstructor() {
        assertThat(type(ClassWithTwoConstructors.class).construct(null, null).stringAndIntegerCalled, equalTo(true));
    }
}
