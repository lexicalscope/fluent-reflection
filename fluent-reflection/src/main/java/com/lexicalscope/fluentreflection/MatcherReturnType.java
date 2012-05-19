package com.lexicalscope.fluentreflection;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

final class MatcherReturnType extends ReflectionMatcher<ReflectedMember> {
    private final Matcher<? super ReflectedClass<?>> returnTypeMatcher;

    public MatcherReturnType(final Matcher<? super ReflectedClass<?>> returnTypeMatcher) {
        this.returnTypeMatcher = returnTypeMatcher;
    }

    @Override
    protected boolean matchesSafely(final ReflectedMember item) {
        final ReflectedClass<?> actualReturnType = item.type();

        if (actualReturnType == null) {
            return false;
        }

        return returnTypeMatcher.matches(actualReturnType);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("callable with return ").appendDescriptionOf(returnTypeMatcher);
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
