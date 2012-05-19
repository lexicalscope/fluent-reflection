package com.lexicalscope.fluentreflection;

import java.util.List;

interface ReflectedFields<T> {
    List<ReflectedField> fields();
    List<ReflectedField> declaredFields();
}
