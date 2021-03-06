package com.lexicalscope.fluentreflection;

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


public class ClassNotFoundRuntimeException extends ReflectionRuntimeException {
    private static final long serialVersionUID = -5339808693443391503L;

    public ClassNotFoundRuntimeException() {
        super();
    }

    public ClassNotFoundRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ClassNotFoundRuntimeException(final String message) {
        super(message);
    }

    public ClassNotFoundRuntimeException(final Throwable cause) {
        super(cause);
    }
}
