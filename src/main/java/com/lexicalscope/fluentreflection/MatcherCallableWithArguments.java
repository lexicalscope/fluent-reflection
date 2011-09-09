/**
 * 
 */
package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;
import static org.hamcrest.Matchers.arrayContaining;

import java.util.List;

import org.hamcrest.Description;

final class MatcherCallableWithArguments extends ReflectionMatcher<ReflectedCallable> {
    private final Class<?>[] expectedArgumentTypes;

    MatcherCallableWithArguments(final Class<?>[] expectedArgumentTypes) {
        this.expectedArgumentTypes = expectedArgumentTypes;
    }

    @Override
    public boolean matchesSafely(final ReflectedCallable arg) {
        final List<Class<?>> actualArgumentTypes =
                convert(arg.argumentTypes(), new ConvertReflectedTypeToClass());

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
        description.appendText("callable with arguments ").appendValue(expectedArgumentTypes);
    }
}