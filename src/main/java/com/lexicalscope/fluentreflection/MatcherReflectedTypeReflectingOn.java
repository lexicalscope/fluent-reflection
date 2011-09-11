/**
 * 
 */
package com.lexicalscope.fluentreflection;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hamcrest.Description;

final class MatcherReflectedTypeReflectingOn extends ReflectionMatcher<ReflectedType<?>> {
    private final Class<?> klass;

    MatcherReflectedTypeReflectingOn(final Class<?> klass) {
        this.klass = klass;
    }

    @Override
    public boolean matchesSafely(final ReflectedType<?> arg) {
        return arg.classUnderReflection().equals(klass);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("reflecting on type ").appendValue(klass);
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj != null && obj.getClass().equals(this.getClass())) {
            return new EqualsBuilder()
                    .append(klass, ((MatcherReflectedTypeReflectingOn) obj).klass)
                    .isEquals();
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return klass.hashCode();
    }
}