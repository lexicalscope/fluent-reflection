package com.lexicalscope.fluentreflection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class TestConvertObjectToClass {
    static class ExampleClass {

    }

    @Test
    public void objectIsConvertedToClass() throws Exception {
        assertThat(new ConvertObjectToClass().convert(new ExampleClass()), equalTo((Object) ExampleClass.class));
    }
}
