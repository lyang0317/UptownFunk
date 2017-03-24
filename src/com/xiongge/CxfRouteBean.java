package com.xiongge;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;

@Order
public class CxfRouteBean implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

	// {url:instance}
	private ConcurrentHashMap<String, Object> cxfInstanceCache = new ConcurrentHashMap<>();
	private ApplicationContext applicationContext;

	@SuppressWarnings("rawtypes")
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Collection<CxfRuleConfig> values = applicationContext.getBeansOfType(CxfRuleConfig.class).values();
		try {
			final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			for (final CxfRuleConfig cxfConfig : values) {
				final Class<?> clazz = cxfConfig.cxfInterface();
				final String configUrl = cxfConfig.url();
				//动态代理
				Object proxyInstance = Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] { clazz }, new InvocationHandler() {
					@SuppressWarnings("unchecked")
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						Annotation[][] parameterAnnotations = method.getParameterAnnotations();
						int index = -1;
						out: for (int i = 0; i < parameterAnnotations.length; i++) {
							Annotation[] as = parameterAnnotations[i];
							for (Annotation annotation : as) {
								//查找注解的位置
								if (annotation instanceof CxfRuleParam) {
									index = i;
									break out;
								}
							}
						}
						String url = configUrl;
						if (index > 0) {
							url = MessageFormat.format(url, cxfConfig.decide(args[index]));
						}
						Object instance = cxfInstanceCache.get(url);
						if (instance == null) {
							factory.setServiceClass(clazz);
							factory.setAddress(url);
							instance = factory.create();
							cxfInstanceCache.put(url, instance);// TODO 线程不安全
						}
						return method.invoke(instance, args);
					}
				});
				beanFactory.registerSingleton(clazz.getName(), proxyInstance);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
