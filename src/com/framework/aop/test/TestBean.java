package com.framework.aop.test;

import javax.jws.WebService;

import com.framework.aop.annotation.Bean;

@Bean
public class TestBean {

	public void sayHello() {
		System.out.println("hello");
	}
}
