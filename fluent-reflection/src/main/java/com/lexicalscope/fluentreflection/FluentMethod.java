package com.lexicalscope.fluentreflection;

import java.lang.reflect.Method;

import org.hamcrest.Matcher;

// Copyright 2011 Tim Wood
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Reflection information about a method.
 *
 * @author tim
 */
public interface FluentMethod extends FluentMember {
    /**
     * The name of the method with any prefix of "get", "set" or "is" removed
     * and the first subsequent character changed to lower case
     *
     * @return The name of the method with any prefix of "get", "set" or "is"
     *         removed and the first subsequent character changed to lower case
     */
    @Override String property();

    @Override Method member();

    FluentMethod rebind(Object reciever);

    int indexOfArg(Matcher<? super FluentClass<?>> matcher);
}
