package com.lexicalscope.fluentreflection;

import static org.hamcrest.Matchers.contains;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

final class MatcherArgumentTypes extends ReflectionMatcher<ReflectedCallable> {
    private final List<Matcher<? super ReflectedClass<?>>> argumentTypeMatchers;

    public MatcherArgumentTypes(final List<? extends Matcher<? super ReflectedClass<?>>> argumentTypeMatchers) {
        this.argumentTypeMatchers = new ArrayList<Matcher<? super ReflectedClass<?>>>(argumentTypeMatchers);
    }

    @Override
    protected boolean matchesSafely(final ReflectedCallable item) {
        final int actualArgumentCount = item.argumentCount();
        final int expectedArgumentCount = argumentTypeMatchers.size();

        if (actualArgumentCount != expectedArgumentCount) {
            return false;
        } else if (actualArgumentCount == 0 && expectedArgumentCount == 0) {
            return true;
        }

        return contains(argumentTypeMatchers).matches(item.argumentTypes());
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable with arguments ").appendList("(", ", ", ")", argumentTypeMatchers);
    }

    @Override
    public final boolean equals(final Object that) {
        if (that != null && that.getClass().equals(this.getClass())) {
            return new EqualsBuilder()
                    .append(argumentTypeMatchers, ((MatcherArgumentTypes) that).argumentTypeMatchers)
                    .isEquals();
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(argumentTypeMatchers).toHashCode();
    }
}
