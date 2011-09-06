package com.lexicalscope.fluentreflection;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import ch.lambdaj.Lambda;


class MatcherArgumentTypes extends ReflectionMatcher<ReflectedConstructor<?>> {
    private final List<Matcher<? super ReflectedType<?>>> argumentTypeMatchers;

    public MatcherArgumentTypes(final List<? extends Matcher<? super ReflectedType<?>>> argumentTypeMatchers) {
        this.argumentTypeMatchers = new ArrayList<Matcher<? super ReflectedType<?>>>(argumentTypeMatchers);
    }

    public MatcherArgumentTypes(final Class<?>[] expectedArgTypes) {
        this(Lambda.convert(expectedArgTypes, new ConvertClassToReflectedTypeMatcher()));
    }

    @Override
    protected boolean matchesSafely(final ReflectedConstructor<?> item) {
        if (item.argumentCount() != argumentTypeMatchers.size()) {
            return false;
        }

        return Matchers.contains(argumentTypeMatchers).matches(item.argumentTypes());
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable with arguments ").appendList("(", ", ", ")", argumentTypeMatchers);
    }
}
