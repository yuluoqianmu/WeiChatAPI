package com.laipai.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 得静态方法得到sping的对像实例
 * 
 * @author lts
 * 
 */
public class ApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext ctx;
	
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}
	public ApplicationContextUtil(){
		if(ctx == null){
			ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		}
	}
	
	
	public static void registerBean(String classzz) {
		Object object = null;
		Class<?> _classzz = null;
		try {
			_classzz = Class.forName(classzz);
			object = _classzz.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// AutowireCapableBeanFactory auto =
		// ctx.getAutowireCapableBeanFactory();
		// auto.autowireBean(object);
		// auto.initializeBean(object, classzz);

		// StaticApplicationContext staticCtx = new
		// StaticApplicationContext(ctx);
		// staticCtx.registerSingleton(classzz, _classzz);
		// String[] arr=ctx.getBeanDefinitionNames();
		// for(String name:arr){
		// staticCtx.register
		// }
		//
		// ctx = staticCtx;
	}

	public static <T> T getBean(String id) {
		try {
			return (T) ctx.getBean(id);
		} catch (org.springframework.beans.factory.NoSuchBeanDefinitionException e) {
			return null;
		}
	}

	public static <T> T getBean(Class<T> id) {
		if(ctx == null){
			ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		}
		if (id == null) return null;
		try {
			return (T) ctx.getBean(id);
		} catch (org.springframework.beans.factory.NoSuchBeanDefinitionException e) {
			return null;
		}
	}
}
