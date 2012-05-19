package com.lexicalscope.fluentreflection;

import java.util.List;

interface ReflectedMethods<T> {
    List<ReflectedMethod> methods();
    List<ReflectedMethod> declaredMethods();
}
