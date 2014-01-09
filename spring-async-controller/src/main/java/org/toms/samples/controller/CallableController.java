package org.toms.samples.controller;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.toms.samples.cache.AnnotationServiceFinder;

@Controller
@RequestMapping("/async/callable")
public class CallableController  {

	protected Log	log	= LogFactory.getLog(this.getClass());
	
	@Autowired
	AnnotationServiceFinder finder;
	

	@RequestMapping("/view/{sid}")
	@ResponseBody
	public Callable<String> callableWithView(@PathVariable final String sid, final String bar, final Model model) {

		if(log.isDebugEnabled()) {
			log.debug("params : sid=" + sid + ", bar=" + bar);
		}
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				
				//invoke and execute target method
				Object obj = finder.invoke(sid, bar);
				
				//return converted json string
				return finder.getObjectMapper().writeValueAsString(obj);
			}
		};
	}

	@RequestMapping("/response-body")
	public @ResponseBody Callable<String> callable() {
		
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				///Thread.sleep(2000);
				return "Callable result";
			}
		};
	}
	
	@RequestMapping("/exception")
	public @ResponseBody Callable<String> callableWithException(
			final @RequestParam(required=false, defaultValue="true") boolean handled) {

		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				if (handled) {
					// see handleException method further below
					throw new IllegalStateException("Callable error");
				}
				else {
					throw new IllegalArgumentException("Callable error");
				}
			}
		};
	}

	@RequestMapping("/custom-timeout-handling")
	public @ResponseBody WebAsyncTask<String> callableWithCustomTimeoutHandling() {

		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return "Callable result";
			}
		};

		return new WebAsyncTask<String>(1000, callable);
	}

	@ExceptionHandler
	@ResponseBody
	public String handleException(IllegalStateException ex) {
		return "Handled exception: " + ex.getMessage();
	}


}
