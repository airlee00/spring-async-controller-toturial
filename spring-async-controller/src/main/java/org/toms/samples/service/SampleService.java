package org.toms.samples.service;

import org.springframework.stereotype.Component;
import org.toms.samples.annotation.ServiceAnnotation;
import org.toms.samples.models.JavaBean;

@Component
public class SampleService {

	@ServiceAnnotation("DD001")
	public JavaBean test(JavaBean bean) {
		bean.setMessage("Hi :" + bean.getName());
		return bean;
	}
}
