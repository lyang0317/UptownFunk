package com.framework.aop.proxy;

import java.lang.reflect.Method;

public abstract class AspectProxy implements Proxy {

	@Override
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		Class<?> cls = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();
		begin();
		try {
			if(true) {
				before(cls, method, params);
				result = proxyChain.doProxyChain();
				after(cls, method, params, result);
			} else {
				result = proxyChain.doProxyChain();
			}
			
		} catch (Exception e) {
			
		}
		return null;
	}

	private void after(Class<?> cls, Method method, Object[] params,
			Object result) {
		// TODO Auto-generated method stub
		
	}

	private void before(Class<?> cls, Method method, Object[] params) {
		// TODO Auto-generated method stub
		
	}

	private void begin() {
		// TODO Auto-generated method stub
		
	}

}
