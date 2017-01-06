package com.framework.aop.proxy;

import java.lang.reflect.Method;

import com.framework.aop.annotation.Aspect;
import com.framework.aop.annotation.Bean;

@Aspect(Bean.class)
public class AspectProxyImpl extends AspectProxy {

	private void after(Class<?> cls, Method method, Object[] params,
			Object result) {
		System.out.println("执行结束");
	}

	private void before(Class<?> cls, Method method, Object[] params) {
		System.out.println("执行准备");
	}

	private void begin() {
		
	}
	
}
