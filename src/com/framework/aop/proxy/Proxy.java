package com.framework.aop.proxy;

public interface Proxy {

	Object doProxy(ProxyChain proxyChain) throws Throwable;
}
