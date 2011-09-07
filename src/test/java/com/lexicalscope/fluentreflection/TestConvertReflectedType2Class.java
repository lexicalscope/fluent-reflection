package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.Reflect.type;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TestConvertReflectedType2Class {
    static class ExampleClass {
    }

    @Test
    public void classIsConvertedToReflectedType() {
        assertThat(
                new ConvertReflectedTypeToClass().convert(type(ExampleClass.class)),
                equalTo((Object) ExampleClass.class));
    }
}
