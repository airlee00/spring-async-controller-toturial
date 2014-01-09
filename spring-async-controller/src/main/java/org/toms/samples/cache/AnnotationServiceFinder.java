package org.toms.samples.cache;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.toms.samples.annotation.ServiceAnnotation;
import org.toms.samples.reflect.SimpleMethodInvoker;

@Component
public class AnnotationServiceFinder implements ApplicationContextAware,InitializingBean{

	protected Log	log	= LogFactory.getLog(this.getClass());
	
	protected Map<String, ServiceInfo> cache = new WeakHashMap<String, ServiceInfo>();

	private ApplicationContext applicationContext;
	
	/** json converter **/
	private ObjectMapper objectMapper = new ObjectMapper();
	{
		objectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Inclusion.NON_NULL);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.applicationContext = arg0;
	}
	

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	/**
	 * find service method from @ServiceAnnocation value
	 * @param serviceId
	 * @return
	 */
	protected ServiceInfo findServiceInfoById(String serviceId) {
		ServiceInfo serviceInfo = cache.get(serviceId);
		if (serviceInfo != null)
			return serviceInfo;

		try {
			//find @Component 
			Map<String, Object> map = applicationContext.getBeansWithAnnotation(org.springframework.stereotype.Component.class);
			for (Entry<String, Object> entry : map.entrySet()) {
				if (entry.getKey().matches(".*Service")) {
					Class<?> targetClass = AopUtils.getTargetClass(entry.getValue());
					Method[] methods = targetClass.getDeclaredMethods();

					for (Method method : methods) {
						ServiceAnnotation serviceAnnotation = method.getAnnotation(ServiceAnnotation.class);
						if (serviceAnnotation != null && serviceAnnotation.value().equals(serviceId)) {
							Class<?>[] parameterTypes = method.getParameterTypes();
							if (parameterTypes.length > 0) {// 임시로 하나만..
								serviceInfo = new ServiceInfo(serviceAnnotation.value(), method, parameterTypes[0], entry.getValue(), false);
								cache.put(serviceAnnotation.value(), serviceInfo);
								return serviceInfo;

							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Cannot find image. It must be on the classpath.", e);
		}
		return null;
	}
	/**
	 * invoke target method 
	 * @param serviceId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object invoke(String serviceId,String jsonString) throws Exception {
		ServiceInfo info = this.findServiceInfoById(serviceId);
		if(info == null)
			return null;
		
		SimpleMethodInvoker mi = new SimpleMethodInvoker(info.getMethod(), info.getTargetObject());
		Object paramObject = convertJsonStringToObject(jsonString,info.getParameterType());
		Object returnObject = mi.invoke(paramObject);
		if(log.isDebugEnabled()) {
			log.debug(" Invoke returnObect=" + returnObject);
		}
		return returnObject;
	}
	
	/**
	 * json형식의 파라미터 처리
	 * @param jsonString
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected Object convertJsonStringToObject(String jsonString,Class<?> paramType) throws Exception {
		
		 if(log.isDebugEnabled()) {
			 log.debug("##jsonstring:" + jsonString);
		 }
		return objectMapper.readValue(jsonString, paramType);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}


}
