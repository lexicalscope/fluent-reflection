package com.lexicalscope.fluentreflection;

import java.lang.reflect.Modifier;

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

/**
 * An enum describing the different levels of visibility offered by Java
 *
 * @author tim
 */
public enum Visibility {
    PUBLIC("public"),
    PRIVATE("private"),
    DEFAULT(""),
    PROTECTED("protected"), ;

    private final String visibility;

    private Visibility(final String visibility) {
        this.visibility = visibility;
    }

    @Override public String toString() {
        return visibility;
    }

    static Visibility visibilityFromModifiers(final int modifiers) {
        if (Modifier.isPublic(modifiers))
        {
            return Visibility.PUBLIC;
        }
        if (Modifier.isPrivate(modifiers))
        {
            return Visibility.PRIVATE;
        }
        if (Modifier.isProtected(modifiers))
        {
            return Visibility.PROTECTED;
        }
        return Visibility.DEFAULT;
    }
}
