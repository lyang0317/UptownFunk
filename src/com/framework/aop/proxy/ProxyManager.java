package com.framework.aop.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyManager {

	public static <T>T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			@Override
			public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams,
					MethodProxy methodProxy) throws Throwable {
				ProxyChain proxyChain = new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList);
				return proxyChain.doProxyChain();
			}
		});
	}
	
	public static <T>T createNativeProxy(final Class<?> targetClass) {
		return (T) Enhancer.create(targetClass, new MethodInterceptor() {
			@Override
			public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams,
					MethodProxy methodProxy) throws Throwable {
				Object result = null;
		        doBefore();
		        result = methodProxy.invokeSuper(targetObject, methodParams);
		        doAfter();
		        return result;
			}

			private void doAfter() {
				System.out.println("after native method invoke");
			}

			private void doBefore() {
				System.out.println("before native method invoke");
			}
		});
	}
	
}
