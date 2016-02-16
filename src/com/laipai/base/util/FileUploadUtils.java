package com.laipai.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.laipai.laiPaiClubInfo.dto.LpClubBean;

public class FileUploadUtils {

	/**文件上传，同时返回路径path（方法封装）*/
	/**
	 * 1：完成文件上传
		  1：将上传的文件统一放置到upload的文件夹下
		  2：将每天上传的文件，使用日期格式的文件夹分开，将每个业务的模块放置统一文件夹下
		  3：上传的文件名要指定唯一，可以使用UUID的方式
		  4：封装一个文件上传的方法，该方法可以支持多文件的上传，即支持各种格式文件的上传
		  5：保存路径path的时候，使用相对路径进行保存，这样便于项目的可移植性

	 * @param file 上传文件
	 * @param string  上传的文件名称
	 * @param string  指定的文件夹（业务模块）
	 * @return
	 */
	public static String fileUpload(File file, String fileName, String model) {
		//基本路径
				String basePath = ServletActionContext.getServletContext().getRealPath("/upload");
				//获取当前时间转换成日期格式的文件夹
				String datePath = DateUtils.dateToStringPath(new Date());
				//获取上传文件名的后缀
				String perfix = fileName.substring(fileName.lastIndexOf("."));
				//文件名(使用uuid作为文件名，防止文件冲突)
				String uuidFileName = UUID.randomUUID().toString()+""+perfix;
				//创建日期文件夹（如果日期格式文件夹不存在，创建）
				String dateModelPath = basePath+"/"+model+datePath;
				File dateModelFile = new File(dateModelPath);
				if(!dateModelFile.exists()){
					dateModelFile.mkdirs();
				}
				
				//文件上传
				file.renameTo(new File(basePath+"/"+model+datePath+"/"+uuidFileName));
				//相对路径
				return "/upload/"+model+datePath+"/"+uuidFileName;
	}
	
	public static void main(String[] args) throws Exception {
		//源文件
		File srcFile = new File("F:\\dir\\a.txt");
		//目标文件
		File destFile = new File("F:\\dir\\dir2xxxxxxxxxx\\a.txt"); 
		//使用工具类（复制）
		//FileUtils.copyFile(srcFile, destFile);
		//将源文件上传到指定路径下
		boolean flag = srcFile.renameTo(destFile);
		System.out.println("flag:"+flag);
		
	}	
	public  static void uploadImage(File in, File out){
		  try {
	    	  //以服务起器文件保存地址和原文件名建立上传文件输出流
	    	   FileOutputStream fos = new FileOutputStream(out);
	    	   //以上传文件建立文件上传流
	    	   FileInputStream fis = new FileInputStream(in);
	    	   //将上传文件写到服务器
	    	   byte[] buffer = new byte[1024];
	    	   int len = 0;
	    	   while((len = fis.read(buffer))>0){
	    	    fos.write(buffer,0,len);
	    	   }
	    	   fos.close();
	    	   fis.close();
	    	 
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		}

}
