package com.lyz.api.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.lyz.api.config.ApiConfig;
import com.lyz.labelData.config.LabelDataConfig;
/**
 * 初始化系统配置
 * @author luzi
 *
 */
public class InitConfigListener implements ServletContextListener{
	
	private static final Logger logger = Logger.getLogger(InitConfigListener.class);

	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent event) {
		
		logger.info("start to init system config...");
		String configDir = event.getServletContext().getInitParameter("configDir");
		ApiConfig.getInstance(configDir);
		LabelDataConfig.getInstance(configDir);
		
		
	}

}
