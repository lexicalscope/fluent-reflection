/**
 * 
 */
package com.lexicalscope.fluentreflection;

import static ch.lambdaj.Lambda.convert;
import static org.hamcrest.Matchers.contains;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

final class MatcherCallableWithArguments extends ReflectionMatcher<ReflectedCallable> {
    private final List<Matcher<? super ReflectedClass<?>>> expectedArgumentTypes;

    MatcherCallableWithArguments(final Class<?>[] expectedArgumentTypes) {
        this(convert(expectedArgumentTypes, new ConvertClassToReflectedTypeMatcher()));
    }

    MatcherCallableWithArguments(final List<? extends Matcher<? super ReflectedClass<?>>> expectedArgumentTypes) {
        this.expectedArgumentTypes = new ArrayList<Matcher<? super ReflectedClass<?>>>(expectedArgumentTypes);
    }

    @Override
    public boolean matchesSafely(final ReflectedCallable arg) {
        final List<ReflectedClass<?>> actualArgumentTypes = arg.argumentTypes();

        if (expectedArgumentTypes == null || expectedArgumentTypes.size() == 0) {
            return actualArgumentTypes.size() == 0;
        }
        if (expectedArgumentTypes.size() != actualArgumentTypes.size()) {
            return false;
        }
        return contains(expectedArgumentTypes).matches(expectedArgumentTypes);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable with arguments ").appendValue(expectedArgumentTypes);
    }
}