package com.framework.aop.helper;

import java.util.HashMap;
import java.util.Map;

import com.framework.aop.annotation.Aspect;
import com.framework.aop.annotation.Bean;

public class BeanHelper {

	private static Map<String, Class> beanContainer = new HashMap<String, Class>();
	private static Map<Class, Object> objectContainer = new HashMap<Class, Object>();

	public static void putBeanToContainer(String className, Class<?> loadClass) {
		boolean annotationFlag = loadClass.isAnnotation();
		boolean interfaceFlag = loadClass.isInterface();
		if(!annotationFlag && !interfaceFlag){
			beanContainer.put(className, loadClass);
			try {
				objectContainer.put(loadClass, loadClass.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static <T>T getBean(Class<T> className) {
		T bean = (T) objectContainer.get(className);
		return bean;
	}
	
	public static Map<Class, Object> getObjectContainer() {
		return objectContainer;
	}
	
}
