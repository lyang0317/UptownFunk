package com.framework.aop.helper;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.framework.aop.config.ResourceConfig;
import com.framework.aop.proxy.AspectProxyImpl;
import com.framework.aop.proxy.CglibProxy;
import com.framework.aop.proxy.ProxyManager;
import com.framework.aop.test.TestBean;

public class ClassHelper {

	static {
		getBeanClassList(ResourceConfig.PACKAGE.getValue());
		try {
			Class.forName("com.framework.aop.helper.AopHelper", true, Thread.currentThread().getContextClassLoader());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void getBeanClassList(String value) {
		String packagePath = value.replace(".", "/");
		URL url = Thread.currentThread().getContextClassLoader().getResource(packagePath);
		String protocol = url.getProtocol();
		if("file".equals(protocol)){
			String filePath = url.getFile();
			File file = new File(filePath);
			File[] dirfiles = file.listFiles(new FileFilter() {
		    	// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
		    	public boolean accept(File file) {
		        	return (file.isDirectory()) || (file.getName().endsWith(".class"));
		    	}
		    });
			for (File dirfile : dirfiles) {
				if(dirfile.isDirectory()) {
					getBeanClassList(value + "." + dirfile.getName());
				} else {
					try {
						String className = value + "." + dirfile.getName().substring(0, dirfile.getName().length() - 6);
						Class<?> loadClass = Thread.currentThread().getContextClassLoader().loadClass(className);
						BeanHelper.putBeanToContainer(className, loadClass);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	
	public static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		classSet.add(AspectProxyImpl.class);
		for (Class<?> cls : BeanHelper.getObjectContainer().keySet()) {
			if(superClass.isAssignableFrom(cls) && !superClass.equals(cls)){
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		classSet.add(AspectProxyImpl.class);
		for (Class<?> cls : BeanHelper.getObjectContainer().keySet()) {
			if(cls.isAnnotationPresent(annotationClass)){
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	public static void main(String[] args) {
		TestBean testBeanProxy = ProxyManager.createNativeProxy(TestBean.class);
		System.out.println(TestBean.class);
		System.out.println(testBeanProxy.getClass());
		testBeanProxy.sayHello();
	}
}
