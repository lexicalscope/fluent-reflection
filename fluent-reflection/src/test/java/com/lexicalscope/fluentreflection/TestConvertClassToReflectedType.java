package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.reflectingOn;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class TestConvertClassToReflectedType {
    static class ExampleClass {
    }

    @Test
    public void classCanBeConvertedToReflectedType() throws Exception {
        assertThat(
                new ConvertClassToReflectedType(new ReflectedTypeFactoryImpl()).convert(ExampleClass.class),
                reflectingOn(ExampleClass.class));
    }
}
