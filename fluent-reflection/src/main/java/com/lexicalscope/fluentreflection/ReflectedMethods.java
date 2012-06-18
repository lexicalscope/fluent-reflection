package com.lexicalscope.fluentreflection;

import java.util.List;

interface ReflectedMethods<T> {
    List<FluentMethod> methods();
    List<FluentMethod> declaredMethods();
}
