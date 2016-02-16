package com.lyz.img;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
/**
 * 阿里云服务器
 * @author luzi
 *
 */
public class AliImgServer {
	public static String bucketName = "ilaipai";
	public static String accessKeyId = "f7ofpKRhiYGHOLDn";
	public static String accessKeySecret = "j5QRM6HPtbefYWnPxwjWbCPqOSF4tG";
	public static String endPoint = "http://oss-cn-beijing.aliyuncs.com/";
	
	private static OSSClient oss = new OSSClient(endPoint, accessKeyId, accessKeySecret);
	
	private static final Logger logger = Logger.getLogger(AliImgServer.class);
	
	/**
	 * 获取图片输入流
	 * @param key
	 * @return
	 */
	public static InputStream getImg(String key){
		
		if(key==null || "".equals(key.trim())){
			
			return null;
		}
		
		OSSObject osObj = oss.getObject(bucketName, key);
		
		if(osObj == null){
			logger.error("fail to get Img object!key="+key);
			return null;
		}
		
		return osObj.getObjectContent();
	}
	
	/**
	 * 传输图片到阿里云图片服务器
	 * @param key
	 * @param file
	 */
	public static void putImg(String key, File file){
		
		if(key==null || "".equals(key.trim()) || file==null){
			return;
		}
		FileInputStream fis = null;
		try {
			ObjectMetadata metadata = new ObjectMetadata();
//			metadata.addUserMetadata("test", "testVal");
			fis = new FileInputStream(file);
			oss.putObject(bucketName, key, fis, metadata);
		} catch (Exception e) {
			logger.error("fail to put img!",e);
		} finally{
			
			close(fis);
		}
		
	}
	
	public static void putImg(String key, InputStream is){
		
		if(key==null || "".equals(key.trim()) || is==null){
			return;
		}
		try {
			logger.info("put img:"+key);
			ObjectMetadata metadata = new ObjectMetadata();
//			metadata.addUserMetadata("test", "testVal");
			oss.putObject(bucketName, key, is, metadata);
		} catch (Exception e) {
			logger.error("fail to put img!",e);
		} finally{
			
			close(is);
		}
		
	}
	
	public static boolean delImg(String key){
		
		if(key==null || "".equals(key.trim())){
			
			return true;
		}
		
		try {
			oss.deleteObject(bucketName, key);
			return true;
		} catch (Exception e) {
			logger.error("fail to delete img!",e);
			return false;
		}
	}
	
	
	public static void close(InputStream is){
		
		try {
			if(is != null){
				is.close();
				is = null;
			}
		} catch (IOException e) {
			logger.error("fail to close inputstream!",e);
		}
	}
	
	
	public static void main(String args[]){
		
		
		
		try {
//			OSSClient oss = new OSSClient(endPoint, accessKeyId, accessKeySecret);
			
			ObjectMetadata metadata = new ObjectMetadata();
//			metadata.addUserMetadata("test", "testVal");
			FileInputStream fis = new FileInputStream(new File("e://test/logo_20150130_03.png"));
			oss.putObject(bucketName, "laipailogo", fis, metadata);
			System.out.println("input");
//			OSSObject obj = oss.getObject(bucketName, "test");
//			InputStream is = obj.getObjectContent();
//			BufferedInputStream bis = new BufferedInputStream(is);
//			byte[] bb = new byte[1024];
//			int count = bis.read(bb);
//			FileOutputStream fos = new FileOutputStream(new File("d://qing2.jpg"));
//			while(count >= 0){
//				fos.write(bb,0,count);
//				count = bis.read(bb);
//			}
			
			
		} catch (OSSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
