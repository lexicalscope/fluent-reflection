package com.lexicalscope.fluentreflection;

import static org.hamcrest.Matchers.contains;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

class MatcherArgumentTypes extends ReflectionMatcher<ReflectedCallable> {
    private final List<Matcher<? super ReflectedType<?>>> argumentTypeMatchers;

    public MatcherArgumentTypes(final List<? extends Matcher<? super ReflectedType<?>>> argumentTypeMatchers) {
        this.argumentTypeMatchers = new ArrayList<Matcher<? super ReflectedType<?>>>(argumentTypeMatchers);
    }

    @Override
    protected boolean matchesSafely(final ReflectedCallable item) {
        if (item.argumentCount() != argumentTypeMatchers.size()) {
            return false;
        } else if (item.argumentCount() == 0 && argumentTypeMatchers.size() == 0) {
            return true;
        }

        return contains(argumentTypeMatchers).matches(item.argumentTypes());
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable with arguments ").appendList("(", ", ", ")", argumentTypeMatchers);
    }
}
