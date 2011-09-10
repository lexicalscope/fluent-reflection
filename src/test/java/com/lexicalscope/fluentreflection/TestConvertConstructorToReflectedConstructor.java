package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectedConstructorReflectingOn;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class TestConvertConstructorToReflectedConstructor {
    static class ExampleClass {
        ExampleClass() {
        }
    }

    @Test
    public void constructorIsConvertedToReflectedConstructor() throws Exception {
        assertThat(new ConvertConstructorToReflectedConstructor<ExampleClass>(new ReflectedTypeFactoryImpl())
                .convert(ExampleClass.class
                        .getDeclaredConstructor()), reflectedConstructorReflectingOn(ExampleClass.class
                .getDeclaredConstructor()));
    }
}
