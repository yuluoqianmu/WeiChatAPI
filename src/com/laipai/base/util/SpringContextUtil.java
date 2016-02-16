package com.laipai.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextUtil implements ApplicationContextAware {
	// Spring应用上下文环境   
	private static ApplicationContext applicationContext;   
	
	public static void init(){
		if(applicationContext == null){
			applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		}
	}

	/**
	 * 描述：注入applicationContext
	 *
	 * @Title: setApplicationContext  
	 * @param arg0
	 * @throws BeansException
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)     
	 * @author:  zhanhh
	 * @date: 2014-12-14 上午11:48:41 
	 * @throws 
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		SpringContextUtil.applicationContext=arg0;

	}
	/**
	 * 
	 * 描述: 获取bean 实例
	 *
	 * @Title: getBean  
	 * @param beanName
	 * @return
	 * @throws BeansException    
	 * @return Object    
	 * @author:  zhanhh
	 * @date: 2014-12-14 上午11:52:30 
	 * @throws
	 */
	public static Object getBean(String beanName)throws BeansException{
		init();
		return applicationContext.getBean(beanName);
	}

}

