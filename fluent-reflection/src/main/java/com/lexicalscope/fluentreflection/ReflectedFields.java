package com.lexicalscope.fluentreflection;

import java.util.List;

interface ReflectedFields<T> {
    List<FluentField> fields();
    List<FluentField> declaredFields();
}
