package com.lexicalscope.fluentreflection;

import org.hamcrest.Matcher;

/*
 * Copyright 2012 Tim Wood
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

public class MemberNotFoundException extends ReflectionRuntimeException {
    private static final long serialVersionUID = -7176337601993269840L;

    private Class<?> klass;
    private Matcher<? super FluentMember> methodMatcher;

    public MemberNotFoundException() {
        super();
    }

    public MemberNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MemberNotFoundException(final String message) {
        super(message);
    }

    public MemberNotFoundException(final Throwable cause) {
        super(cause);
    }

    public MemberNotFoundException(final Class<?> klass, final Matcher<? super FluentMember> methodMatcher) {
        this(String.format("unable to find member matching <%s> in %s", methodMatcher, klass));
        this.klass = klass;
        this.methodMatcher = methodMatcher;
    }

    public Class<?> getKlass() {
        return klass;
    }

    public Matcher<? super FluentMember> getMemberMatcher() {
        return methodMatcher;
    }
}
