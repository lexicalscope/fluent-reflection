package com.lexicalscope.fluentreflection;

import org.hamcrest.Matcher;

/*
 * Copyright 2011 Tim Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

final class MethodNotFoundException extends MemberNotFoundException {
    private static final long serialVersionUID = 6649785414761324558L;
    private Matcher<? super FluentMethod> methodMatcher;

    public MethodNotFoundException() {
        super();
    }

    public MethodNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MethodNotFoundException(final String message) {
        super(message);
    }

    public MethodNotFoundException(final Throwable cause) {
        super(cause);
    }

    public MethodNotFoundException(final Class<?> klass, final Matcher<? super FluentMethod> methodMatcher) {
        super(String.format("unable to find method matching <%s> in %s", methodMatcher, klass));
        this.methodMatcher = methodMatcher;
    }

    public Matcher<? super FluentMethod> getMethodMatcher() {
        return methodMatcher;
    }
}
