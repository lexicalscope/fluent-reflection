package com.lexicalscope.fluentreflection;

import java.lang.reflect.Member;
import java.util.List;

public interface FluentMember extends FluentAnnotated {
    /**
     * Type that declares the member
     *
     * @return type that declares the member
     */
    FluentClass<?> declarer();

    /**
     * The underlying member (class, field, constructor)
     *
     * @return the underlying member
     */
    Member member();

    /**
     * The name of the member. Constructors are called &lt;init&gt;
     *
     * @return the name of the member
     */
    String name();

    /**
     * The name of the member as a property name. Bean-style prefixes will be stripped from the member name.
     *
     * @return the name of the member as a property
     */
    String property();

    /**
     * The number of arguments that the member takes when called
     *
     * @return the number of arguments that the member takes when called
     */
    int argCount();

    /**
     * The types of the arguments used when the member is called
     *
     * @return the types of the arguments used when the member is called
     */
    List<FluentClass<?>> args();

    /**
     * The type of the member (the type of the field, or the return type of the method)
     *
     * @return the type of the member
     */
    FluentClass<?> type();

    /**
     * Call the member using the given arguments
     *
     * @param args the arguments to use in the call
     *
     * @return the value returned by the call
     */
    Object callRaw(Object... args);

    /**
     * Call the member using the given arguments
     *
     * @param args the arguments to use in the call
     *
     * @return the value returned by the call wrapped in a fluent reflection wrapper
     */
    FluentObject<?> call(Object... args);

    /**
     * Used to cast the result of a call on this member
     *
     * @param returnType the type expected as the result of the call
     *
     * @return object that can offers the call with the result cast to the given type
     */
    <T> Call<T> as(Class<T> returnType);

    /**
     * true iff the member is static
     *
     * @return true iff the member is static
     */
    boolean isStatic();

    /**
     * true iff the member is final
     *
     * @return true iff the member is final
     */
    boolean isFinal();

    /**
     * the visibility of the member
     *
     * @return the visibility of the member
     */
    Visibility visibility();
}
