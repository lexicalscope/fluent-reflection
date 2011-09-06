/**
 * 
 */
package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;
import static org.hamcrest.Matchers.arrayContaining;

import java.util.List;

import org.hamcrest.Description;


final class MatcherMethodWithArguments extends ReflectionMatcher<ReflectedMethod> {
    private final Class<?>[] expectedArgumentTypes;

    MatcherMethodWithArguments(Class<?>[] expectedArgumentTypes) {
        this.expectedArgumentTypes = expectedArgumentTypes;
    }

    @Override
    public boolean matchesSafely(final ReflectedMethod arg) {
        final List<Class<?>> actualArgumentTypes =
                convert(arg.getArgumentTypes(), new ConvertReflectedType2Class());

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