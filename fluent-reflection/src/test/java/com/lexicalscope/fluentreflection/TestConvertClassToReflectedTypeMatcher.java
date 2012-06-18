package com.lexicalscope.fluentreflection;

import static com.lexicalscope.fluentreflection.FluentReflection.type;
import static org.junit.Assert.assertThat;

import org.hamcrest.Matcher;
import org.junit.Test;

public class TestConvertClassToReflectedTypeMatcher {
    static class ExampleClass {

    }

    @Test
    public void classConvertedToMatcherForReflectedType() throws Exception {
        final Matcher<FluentClass<?>> matcherUnderTest =
                new ConvertClassToReflectedTypeMatcher().convert(ExampleClass.class);

        assertThat(type(ExampleClass.class), matcherUnderTest);
    }
}
