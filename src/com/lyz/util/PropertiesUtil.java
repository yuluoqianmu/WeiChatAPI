package com.lyz.util;

import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

public class PropertiesUtil {

	private Properties pro = null;

	private FileInputStream fis = null;
	
//	private FileWriter writer = null;
	String filePath = null;
	
	public PropertiesUtil(String filePath) {
		this.filePath = filePath;
		try {
//			writer = new FileWriter(new File(filePath));
			fis = new FileInputStream(new File(filePath));
			pro = new Properties();
			pro.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public String getProperty(String key) {

		return pro.getProperty(key);
	}

	public void setProperty(String key, String val) {
		pro.setProperty(key, val);
	}
	
	public void storeProperties(Writer writer, String key, String val, String comments){
		try {
			pro.store(writer, comments);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
