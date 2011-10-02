package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectedConstructorReflectingOn;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

import com.google.inject.TypeLiteral;

public class TestConvertConstructorToReflectedConstructor {
    static class ExampleClass {
        ExampleClass() {}
    }

    @Test public void constructorIsConvertedToReflectedConstructor() throws Exception {
        final TypeLiteral<ExampleClass> exampleClass = TypeLiteral.get(ExampleClass.class);
        assertThat(new ConvertConstructorToReflectedConstructor<ExampleClass>(
                new ReflectedTypeFactoryImpl(),
                exampleClass)
                .convert(ExampleClass.class
                        .getDeclaredConstructor()), reflectedConstructorReflectingOn(ExampleClass.class
                .getDeclaredConstructor()));
    }
}
