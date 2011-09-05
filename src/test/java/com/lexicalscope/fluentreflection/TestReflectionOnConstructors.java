package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.Reflect.type;
import static com.lexicalscope.fluentreflection.matchers.ReflectionMatchers.constructorHasThisManyArguments;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.lexicalscope.fluentreflection.matchers.ReflectionMatchers;

public class TestReflectionOnConstructors {
    static class TwoConstructorsWithDifferentNumbersOfArguments {
        public TwoConstructorsWithDifferentNumbersOfArguments(final String argumentZero) {
        }

        public TwoConstructorsWithDifferentNumbersOfArguments(final String argumentZero, final Integer argumentOne) {
        }
    }

    @Test
    public void canFindConstructorsByTheNumberOfArguments() {
        assertThat(type(TwoConstructorsWithDifferentNumbersOfArguments.class).constructors(
                constructorHasThisManyArguments(2)), Matchers.contains(ReflectionMatchers.constructorHasArguments(
                String.class,
                Integer.class)));
    }
}
