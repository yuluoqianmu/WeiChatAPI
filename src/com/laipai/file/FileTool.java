package com.laipai.file;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Writer;

import org.apache.log4j.Logger;

/**
 * 主要用于文件的读写，以行为单位
 * @author luyongzhao
 *
 */
public class FileTool {
	private static final Logger logger = Logger.getLogger(FileTool.class);
	
	private String filePath = null;
	
	
	private BufferedReader reader = null;
	
	private BufferedOutputStream bos = null;
	private FileOutputStream fis = null;
//	private OutputStream os = null;
	
	public FileTool(String filePath){
		this.filePath = filePath;
	}
	
	public void openReader(){
		File file = new File(this.filePath);
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			reader = new BufferedReader(new FileReader(file));
		} catch (Exception e) {
			logger.error("fail to open file:"+filePath, e);
		}
	}
	
	public String getNextLine(){
		try {
			return reader.readLine();
		} catch (IOException e) {
			logger.error("fail to read new line from file:"+filePath, e);
			return null;
		}
	}
	
	public void closeReader(){
		try {
			if(reader != null){
				reader.close();
				reader = null;
			}
		} catch (IOException e) {
			logger.warn("fail to close reader",e);
		}
	}
	
	public boolean openOutputStream(boolean append){
		try {
			fis = new FileOutputStream(new File(filePath),append);
			bos = new BufferedOutputStream(fis);
		} catch (FileNotFoundException e) {
			logger.error("fail to open writer:"+filePath, e);
			return false;
		}
		
		return true;
	}
	/**
	 * 写入文件，多线程同步
	 * @param bytes
	 * @param off
	 * @param length
	 */
	public synchronized void writeNewBytes(byte[] bytes, int off, int length){
		if(bytes == null){
			return;
		}
		
		try {
			bos.write(bytes, off, length);
			bos.flush();
		} catch (IOException e) {
			logger.error("fail to write new bytes to file:"+filePath,e);
		}catch (Exception e){
			logger.error(new String(bytes)+"\noff="+off+"\nlength="+length,e);
		}
		
	}
	
	public void closeOutputStream(){
		try {
			/*关闭之前，需要flush，防止丢数据*/
			bos.flush();
			fis.flush();
			if(bos != null){			
				bos.close();
				bos = null;
			}
			if(fis != null){				
				fis.close();
				fis = null;
			}
		} catch (IOException e) {
			logger.warn("fail to close outputstream.",e);
		}		
	}
	
	public static void main(String args[]){
		
		FileTool tool = new FileTool("d://testlog/test.txt");
		tool.openOutputStream(true);
//		int count = 1000;
//		int i = 0;
//		while(i<=count){
//			i++;
//			String line = i+":aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\n";
//			tool.writeNewBytes(line.getBytes(), 0, line.length());
//			
//		}
		String tmp = "\n";
		tool.writeNewBytes(tmp.getBytes(), 0, tmp.length());
		tool.closeOutputStream();
	}

}
