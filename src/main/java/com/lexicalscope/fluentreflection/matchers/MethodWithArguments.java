/**
 * 
 */
package com.lexicalscope.fluentreflection.matchers;

import static ch.lambdaj.Lambda.convert;
import static org.hamcrest.Matchers.arrayContaining;

import java.util.List;

import org.hamcrest.Description;

import com.lexicalscope.fluentreflection.ReflectedMethod;
import com.lexicalscope.fluentreflection.ReflectionMatcher;

final class MethodWithArguments extends ReflectionMatcher<ReflectedMethod> {
    private final Class<?>[] expectedArgumentTypes;

    MethodWithArguments(Class<?>[] expectedArgumentTypes) {
        this.expectedArgumentTypes = expectedArgumentTypes;
    }

    @Override
    public boolean matchesSafely(final ReflectedMethod arg) {
        final List<Class<?>> actualArgumentTypes =
                convert(arg.getArgumentTypes(), new ReflectedType2ClassConvertor());

        if (expectedArgumentTypes == null || expectedArgumentTypes.length == 0) {
            return actualArgumentTypes.size() == 0;
        }
        if (expectedArgumentTypes.length != actualArgumentTypes.size()) {
            return false;
        }
        return arrayContaining(actualArgumentTypes.toArray(new Class[actualArgumentTypes.size()])).matches(
                expectedArgumentTypes);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("method with arguments ").appendValue(expectedArgumentTypes);
    }
}