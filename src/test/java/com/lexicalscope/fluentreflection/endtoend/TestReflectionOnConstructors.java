package com.lexicalscope.fluentreflection.endtoend;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static com.lexicalscope.fluentreflection.ReflectionMatchers.callableHasThisManyArguments;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.lexicalscope.fluentreflection.ReflectionMatchers;


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
                callableHasThisManyArguments(2)), Matchers.contains(ReflectionMatchers.callableHasArguments(
                String.class,
                Integer.class)));
    }
}
