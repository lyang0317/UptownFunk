package com.framework.aop.config;

public enum ResourceConfig {

	PACKAGE("com.framework.aop.test");
	
	private String value;
	
	private ResourceConfig(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
