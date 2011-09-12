package com.lexicalscope.fluentreflection;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

final class MatcherReturnType extends ReflectionMatcher<ReflectedCallable> {
    private final Matcher<? super ReflectedClass<?>> returnTypeMatcher;

    public MatcherReturnType(final Matcher<? super ReflectedClass<?>> returnTypeMatcher) {
        this.returnTypeMatcher = returnTypeMatcher;
    }

    @Override
    protected boolean matchesSafely(final ReflectedCallable item) {
        if (item.returnType() == null) {
            return false;
        }

        return returnTypeMatcher.matches(item.returnType());
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable with return type ").appendDescriptionOf(returnTypeMatcher);
    }

    @Override
    public final boolean equals(final Object that) {
        if (that != null && that.getClass().equals(this.getClass())) {
            return new EqualsBuilder()
                    .append(returnTypeMatcher, ((MatcherReturnType) that).returnTypeMatcher)
                    .isEquals();
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(returnTypeMatcher).toHashCode();
    }
}
