package org.toms.samples.cache;

import java.lang.reflect.Method;

public class ServiceInfo {

	private String serviceId;
	private Class<?> parameterType ;//list일 경우는 genericType 
	private boolean isListParameterType ; //java.util.List 타입여부 
	private Method method ;
	private Object targetObject;
	
	
	public ServiceInfo(String serviceId, Method method, Class<?> parameterType, Object targetObject, boolean isListParameterType) {
		super();
		this.serviceId = serviceId;
		this.parameterType = parameterType;
		this.isListParameterType = isListParameterType;
		this.method = method;
		this.targetObject = targetObject;
	}

	
	public boolean isListParameterType() {
    	return isListParameterType;
    }


	public String getServiceId() {
		return serviceId;
	}

	public Class<?> getParameterType() {
		return parameterType;
	}


	public Method getMethod() {
		return method;
	}


	public void setMethod(Method method) {
		this.method = method;
	}


	public Object getTargetObject() {
		return targetObject;
	}


	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}


	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}


	public void setParameterType(Class<?> parameterType) {
		this.parameterType = parameterType;
	}


	public void setListParameterType(boolean isListParameterType) {
		this.isListParameterType = isListParameterType;
	}


}
