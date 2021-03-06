package org.fuwjin.wheatgrass;

import javax.inject.Provider;

import org.fuwjin.wheatgrass.binding.Binding;
import org.fuwjin.wheatgrass.context.Context;

/**
 * The primary means of constructing a root {@link StandardInjector}. Instances of this
 * type are normally generated by {@link Wheatgrass#newInjector()}.
 * <p>
 * The general use case for a builder is the following: <code><pre>
 * StandardInjector injector = Wheatgrass.newInjector()
 *     .withConstant(someConstant, someOtherConstant)
 *     .withTypedConstant(Foo.class, someFooSubclassInstance)
 *     .withContext(someContext, someOtherContext)
 *     .withMembers(someObject, someOtherObject)
 *     .build();
 * </pre></code> An injector is a {@link Context}, and a Context is a mapping
 * between {@link Key}s and {@link Binding}s. There are three main ways to
 * include bindings in an injector.
 * <p>
 * The first way is as a constant. A constant is a direct binding between a type
 * and an object.
 * <p>
 * The second way to include bindings is with a child context. All bindings from
 * the child are exposed through the injector.
 * <p>
 * The last way is through member reflection. This is the most complicated to
 * understand, the simplest to write, and the most powerful abstraction in
 * Wheatgrass. The rules for binding exposure are as follows:
 * <ul>
 * <li>All fields are exposed as bindings. Their value object is bound to the
 * name, type, generic type, and annotations of the field.
 * <li>All Provider<X> fields are exposed as bindings to X. Their
 * {@link Provider} value is bound as a provider to the type and generic type of
 * X, as well as to the name and annotations of the field.
 * <li>Any method annotated with @Provides is exposed as a binding. The method
 * invocation is bound to the name, return type, generic return type and
 * annotations of the method.
 * <li>Any method annotation with @Provides returning Provider<X> is exposed as
 * a binding to X. The {@link Provider} returned from a method invocation is
 * bound as a provider to the type and generic type of X, as well as to the name
 * and annotations of the method.
 * <li>Any native method with a single argument which is assignable to its
 * return type will bind the argument type's binding to the name, return type,
 * generic return type and annotations of the method.
 * </ul>
 * 
 * @author fuwjax
 * @see InjectorBuilder
 * 
 */
public interface RootInjectorBuilder {
	/**
	 * Adds a set of constants to the {@link StandardInjector}. Each constant will be
	 * bound to its instantiated class.
	 * 
	 * @param constants
	 *            the set of constants
	 * @return this builder
	 */
	RootInjectorBuilder withConstants(Object... constants);

	/**
	 * Adds a constant bound to a ancestor type. Note that if
	 * {@code type.equals(constant.getClass())} then this is equivalent to
	 * {@code withConstants(constant)}.
	 * 
	 * @param type
	 *            the bound type
	 * @param constant
	 *            the bound constant
	 * @return this builder
	 */
	<T> RootInjectorBuilder withConstant(Class<T> type, T constant);

	/**
	 * Adds a constant bound to a key.
	 * 
	 * @param key
	 *            the bound key
	 * @param constant
	 *            the bound constant
	 * @return this builder
	 */
	<T> RootInjectorBuilder withConstant(Key<T> key, T constant);

	/**
	 * Adds a set of contexts.
	 * 
	 * @param contexts
	 *            the contexts
	 * @return this builder
	 */
	RootInjectorBuilder withContext(Context... contexts);

	/**
	 * Adds a set of members reflectively bound from each of the context
	 * objects. For each object, the following bindings will be created.
	 * <ul>
	 * <li>All fields are exposed as bindings. Their value object is bound to
	 * the name, type, generic type, and annotations of the field.
	 * <li>All Provider<X> fields are exposed as bindings to X. Their
	 * {@link Provider} value is bound as a provider to the type and generic
	 * type of X, as well as to the name and annotations of the field.
	 * <li>Any method annotated with @Provides is exposed as a binding. The
	 * method invocation is bound to the name, return type, generic return type
	 * and annotations of the method.
	 * <li>Any method annotation with @Provides returning Provider<X> is exposed
	 * as a binding to X. The {@link Provider} returned from a method invocation
	 * is bound as a provider to the type and generic type of X, as well as to
	 * the name and annotations of the method.
	 * <li>Any native method with a single argument which is assignable to its
	 * return type will bind the argument type's binding to the name, return
	 * type, generic return type and annotations of the method.
	 * </ul>
	 * 
	 * @param context
	 *            the context objects
	 * @return this builder
	 */
	RootInjectorBuilder withMembers(Object... context);

	/**
	 * Creates an injector from the registered constants, contexts, and members.
	 * 
	 * @return the injector
	 */
	Injector build();

}