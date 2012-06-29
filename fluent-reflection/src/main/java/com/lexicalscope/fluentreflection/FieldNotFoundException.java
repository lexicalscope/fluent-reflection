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

public class FieldNotFoundException extends ReflectionRuntimeException {
    private static final long serialVersionUID = 6649785414761324558L;
    private Class<?> klass;
    private Matcher<? super FluentField> fieldMatcher;

    public FieldNotFoundException() {
        super();
    }

    public FieldNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FieldNotFoundException(final String message) {
        super(message);
    }

    public FieldNotFoundException(final Throwable cause) {
        super(cause);
    }

    public FieldNotFoundException(final Class<?> klass, final Matcher<? super FluentField> fieldMatcher) {
        this(String.format("unable to find field matching <%s> in %s", fieldMatcher, klass));
        this.klass = klass;
        this.fieldMatcher = fieldMatcher;
    }

    public Class<?> getKlass() {
        return klass;
    }

    public Matcher<? super FluentField> getFieldMatcher() {
        return fieldMatcher;
    }
}
