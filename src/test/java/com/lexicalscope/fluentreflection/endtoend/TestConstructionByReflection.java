package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.Reflect.type;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class TestConstructionByReflection {
    static class ClassWithDefaultConstructor {
        private final boolean called;

        public ClassWithDefaultConstructor() {
            called = true;
        }
    }

    @Test
    public void constructUsingDefaultConstructor() {
        assertThat(type(ClassWithDefaultConstructor.class).construct().called, equalTo(true));
    }
}
