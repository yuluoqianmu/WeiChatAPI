package com.laipai.app.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 得到自定义的log4j 的logger 重新设定logger的存放路径
 * 
 * @author ts
 * 
 */
public class APPLogger {

	public static Logger getLogger(Class<?> classzz) {
		return Logger.getLogger(classzz);
	}

	public static void initLogger(String rootPath) {
		String path = rootPath + "/WEB-INF/log/";
		System.out.println("init logger:" + path);
		if (path == null) return;
		File file = new File(path);
		if (!file.exists()) file.mkdirs();
		InputStream inputStream = null;
		try {// 先装载默认配置
			inputStream = new FileInputStream(new File(rootPath + "/WEB-INF/classes/log4j.properties"));

			Properties pro = new Properties();
			pro.load(inputStream);

			pro.setProperty("log4j.appender.R.File", path + pro.getProperty("log4j.appender.R.File"));

			PropertyConfigurator.configure(pro);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
