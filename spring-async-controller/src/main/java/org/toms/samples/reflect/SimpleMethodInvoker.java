
package org.toms.samples.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.util.Assert;


public class SimpleMethodInvoker  {

	/** {@link Method} */
	protected Method method;

	/** 대상 객체 */
	protected Object targetObject;

	/**
	 * 생성자.
	 */
	public SimpleMethodInvoker() {
		super();
	}

	/**
	 * 생성자.
	 * 
	 * @param method
	 *            {@link Method}.
	 * @param targetObject
	 *            대상 객체
	 * @throws IllegalArgumentException
	 *             <code>method</code>, <code>targetObject</code>가
	 *             <code>null</code>인 경우.
	 */
	public SimpleMethodInvoker(Method method, Object targetObject) {
		super();
		Assert.notNull(method, "Argument 'method' must not be null.");
		Assert.notNull(targetObject,
				"Argument 'targetObject' must not be null.");
		this.method = method;
		this.targetObject = targetObject;
	}

	/**
	 * {@link #method}를 설정한다.
	 * 
	 * @param method
	 *            {@link Method}.
	 * @throws IllegalArgumentException
	 *             <code>method</code>가 <code>null</code>인 경우.
	 */
	public void setMethod(Method method) {
		Assert.notNull(method, "Argument 'method' must not be null.");
		this.method = method;
	}

	/**
	 * {@link #targetObject}를 설정한다.
	 * 
	 * @param targetObject
	 *            대상 객체.
	 * @throws IllegalArgumentException
	 *             <code>targetObject</code>가 <code>null</code>인 경우.
	 */
	public void setTargetObject(Object targetObject) {
		Assert.notNull(targetObject,
				"Argument 'targetObject' must not be null.");
		this.targetObject = targetObject;
	}

	public Method getMethod() {
		return method;
	}

	public Object getTargetObject() {
		return targetObject;
	}

	public Object invoke(Object... args) throws IllegalAccessException,
			InvocationTargetException {
		return method.invoke(targetObject, args);
	}

}
