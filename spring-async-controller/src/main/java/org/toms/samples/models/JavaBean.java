package org.toms.samples.models;

import java.io.Serializable;

public class JavaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sid;
	
	private String name;
	
	private String message;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "JavaBean [sid=" + sid + ", name=" + name + ", message=" + message + "]";
	}

	
		

}
