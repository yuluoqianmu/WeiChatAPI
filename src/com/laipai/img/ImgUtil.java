package com.laipai.img;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.Map.Entry;

import javax.imageio.ImageIO;


import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.laipai.app.common.Tools;
import com.laipai.base.util.LaipaiConstants;
import com.laipai.util.BaseTypeUtil;
import com.lyz.img.AliImgServer;
import com.lyz.util.LaiPaiEntityIdGenerator;


/**
 * 图片路径替换
 * 
 * @author luzi
 * 
 */
public class ImgUtil {

	private static final Logger logger = Logger.getLogger(ImgUtil.class);
	/**
	 * 通过文件路径截取文件名称
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath){
		
		if(filePath == null || "".equals(filePath.trim())){
			return null;
		}
		
		return filePath.substring(filePath.lastIndexOf('/')+1);
	}
	/**
	 * 生成图片文件名称
	 * @param imgType
	 * @return
	 */
	public static String generateImgName(String imgType){
		
		return LaiPaiEntityIdGenerator
		.generatorId(LaiPaiEntityIdGenerator.typePic)
		+ "." + imgType;
	}

	/**
	 * 返回实际可访问的图片的url地址
	 * 
	 * @param filePath
	 * @return
	 */
//	public static String getImgUrl(String filePath) {
//
//		if (filePath == null || "".equals(filePath)) {
//
//			return "";
//		}
//
//		if (filePath.startsWith("/upload")) {
//			return LaipaiConstants.serverUrl + filePath;
//		}
//
//		return filePath.replace(LaipaiConstants.UPLOAD_ABSOLUTE_IMG,
//				LaipaiConstants.upload);
//	}
	public static String getImgUrl(String filePath,String picType) {
		
		return getImgUrl(filePath, picType, 700);

//		if (filePath == null || "".equals(filePath)) {
//
//			return "";
//		}
//		if(!"webp".equals(picType)){
//			picType = "jpg";
//		}
//		
//		String subStr = filePath;
//		/*如果图片来自图片服务器,则获取子串*/		
//		if (filePath.startsWith(LaipaiConstants.imgUrl)) {
//			
//			subStr = BaseTypeUtil.getSubString(filePath, LaipaiConstants.imgUrl, "@");
//			/*如果截取失败，则直接返回*/
//			if(subStr == null){
//				return filePath;
//			}
//		}else if(filePath.startsWith("http")){/*图片来自其他http地址*/
//			
//			return filePath;
//		}
//		/*拼凑字符串*/
//		return LaipaiConstants.imgUrl+subStr+"@700w_700h."+picType;
	}
	
	public static String getImgUrl(String filePath) {

		return getImgUrl(filePath, "jpg");
	}
	
	public static String getImgUrl(String filePath,String picType, int size) {

		if (filePath == null || "".equals(filePath)) {

			return "";
		}
		if(!"webp".equals(picType)){
			picType = "jpg";
		}
		
		String subStr = filePath;
		/*如果图片来自图片服务器,则获取子串*/		
		if (filePath.startsWith(LaipaiConstants.imgUrl)) {
			
			subStr = BaseTypeUtil.getSubString(filePath, LaipaiConstants.imgUrl, "@");
			/*如果截取失败，则直接返回*/
			if(subStr == null){
				return filePath;
			}
		}else if(filePath.startsWith("http")){/*图片来自其他http地址*/
			
			return filePath;
		}
		/*拼凑字符串*/
		return LaipaiConstants.imgUrl+subStr+"@"+size+"w_"+size+"h."+picType;
	}

	/**
	 * 返回合法的文件类型
	 * 
	 * @param fileName
	 * @return 不合法的文件类型返回null
	 */
	public static String getFileType(String fileName) {

		if (fileName == null || "".equals(fileName.trim())
				|| !fileName.contains(".")) {
			return null;
		}

		int index = fileName.lastIndexOf('.');
		String fileType = fileName.substring(index + 1);
		/* 图片类型合法则返回 */
		if (isValidFileType(fileType)) {

			return fileType;
		}
		logger.error("invalid img fileType:" + fileType);
		return null;

	}
	/**
	 * 是否为合法图片格式
	 * @param fileType
	 * @return
	 */
	public static boolean isValidFileType(String fileType){
		
		if(fileType == null){
			return false;
		}
		
		fileType = fileType.toLowerCase();
		
		boolean flag = "jpg".equals(fileType) || "jpeg".equals(fileType)
		|| "png".equals(fileType) || "gif".equals(fileType);
		
		if(!flag){
			logger.error("img type should be in (jpg,jpeg,png,gif):"+fileType);
		}
		
		return flag;
	}

	/**
	 * 保存图片
	 * 
	 * @param fileName2File
	 * @return 图片地址
	 */
	public static String saveImg(Entry<String, FileItem> fileName2File,
			String filePath) {

		if (fileName2File == null || fileName2File.getValue() == null) {
			return null;
		}
		/* 保存文件 */
		String realFileName = null;
		String filename = null;
		try {
			/* 判断图片文件夹是否存在，不存在则创建 */
//			File forder = new File(filePath);
//			if (!forder.exists()) {
//				forder.mkdirs();
//			}
			/* 文件格式校验 */
			filename = fileName2File.getKey();
			String fileType = getFileType(filename);
			/* 图片文件不是合法的，则直接返回null */
			if (fileType == null) {
				return null;
			}

			InputStream in = fileName2File.getValue().getInputStream();
//			realFileName = LaiPaiEntityIdGenerator
//					.generatorId(LaiPaiEntityIdGenerator.typePic)
//					+ "." + fileType;
//			Tools.writeFile(filePath + "/" + realFileName, in);
			
			
			/*输入流转换成字节数组*/
//			byte[] bytes = readInputStream(in);
//			logger.info("android save img:length="+bytes.length/1024);
//			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
//			
			
			realFileName = LaiPaiEntityIdGenerator
			.generatorId(LaiPaiEntityIdGenerator.typePic)+"";
			
			AliImgServer.putImg(realFileName, in);
		} catch (Exception e) {
			logger.error("fail to save img file:" + filename, e);
			return null;
		}

//		return filePath + "/" + realFileName;
		
		return realFileName;

	}
	/**
	 * 保存图片文件
	 * @param in
	 * @param fileName 文件名
	 * @param filePath 文件路径
	 * @return
	 */
	public static String saveImg(File in, String fileName,String filePath){
		
		String fileType = getFileType(fileName);
		
		return saveImgNoName(in, fileType, filePath);
		
	}
	
	public static byte[] readInputStream(InputStream inputStream) throws Exception{
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        inputStream.close();
        return outputStream.toByteArray();
    }
	/**
	 * 保存图片文件
	 * @param in
	 * @param fileType 文件后缀名
	 * @param filePath 文件路径
	 * @return
	 */
	public static String saveImgNoName(File in, String fileType,String filePath){
		
		if(in==null || filePath==null){
			return null;
		}
		String realFileName = null;
		FileInputStream fis = null;
		try {
//			/*验证是否为合法的图片文件*/
//			String fileType = getFileType(fileName);
			/* 图片文件不是合法的，则直接返回null */
			if (fileType == null) {
				return null;
			}
//			/*生成文件名称*/
//			realFileName = LaiPaiEntityIdGenerator
//			.generatorId(LaiPaiEntityIdGenerator.typePic)
//			+ "." + fileType;
//			fis = new FileInputStream(in);
//			/*写入文件*/
//			Tools.writeFile(filePath + "/" + realFileName, fis);
			realFileName = LaiPaiEntityIdGenerator
			.generatorId(LaiPaiEntityIdGenerator.typePic)+"";
			
			logger.info("android put img:"+realFileName);
			
			AliImgServer.putImg(realFileName, in);
		} catch (Exception e) {
			logger.error("fail to save img fileType:" + fileType, e);
			return null;
		} finally{
			
			/*关闭输入流*/
			close(fis);
		}
		
//		return filePath + "/" + realFileName;
		return realFileName;
	}
	
	public static String saveImgNoName(byte[] in, String fileType,String filePath){
		
		if(in==null || filePath==null){
			return null;
		}
		String realFileName = null;
		ByteArrayInputStream bis = null;
		try {
//			/*验证是否为合法的图片文件*/
//			String fileType = getFileType(fileName);
			/* 图片文件不是合法的，则直接返回null */
			if (fileType == null) {
				return null;
			}
//			/*生成文件名称*/
//			realFileName = LaiPaiEntityIdGenerator
//			.generatorId(LaiPaiEntityIdGenerator.typePic)
//			+ "." + fileType;
			bis = new ByteArrayInputStream(in);
//			/*写入文件*/
//			Tools.writeFile(filePath + "/" + realFileName, bis);
			realFileName = LaiPaiEntityIdGenerator
			.generatorId(LaiPaiEntityIdGenerator.typePic)+"";
			
			AliImgServer.putImg(realFileName, bis);
		} catch (Exception e) {
			logger.error("fail to save img fileType:" + fileType, e);
			return null;
		} finally{
			
			/*关闭输入流*/
			close(bis);
		}
		
//		return filePath + "/" + realFileName;
		return realFileName;
	}
	/**
	 * ios专用图片保存方法，需要将ios的上传的base64编码的字符串进行解码
	 * @param filePath
	 * @param fileType
	 * @param imgStr
	 * @return
	 */
	public static String saveImg(String filePath, String fileType, String imgStr){
		
		if(filePath==null || fileType==null || imgStr==null){
			return null;
		}
		/*图片格式不合法直接返回*/
		if(!isValidFileType(fileType)){

			return null;
		}
		String realFileName = null;
		InputStream is = null;
		try {
			/*生成文件名称*/
//			realFileName = LaiPaiEntityIdGenerator
//			.generatorId(LaiPaiEntityIdGenerator.typePic)
//			+ "." + fileType;
			/*获取图片输入流*/
			is = generateImage(imgStr);
			/*写入文件*/
//			Tools.writeFile(filePath + "/" + realFileName, is);
			
			realFileName = LaiPaiEntityIdGenerator
			.generatorId(LaiPaiEntityIdGenerator.typePic)+"";
			
			logger.info("ios put img:"+realFileName);
			
			AliImgServer.putImg(realFileName, is);
		} catch (Exception e) {
			logger.error("fail to save img file for ios!", e);
			return null;
		} finally{
			
			close(is);
		}
		
//		return filePath + "/" + realFileName;
		return realFileName;
	}
	
	/**
	* 将字符串转为图片（ios使用）
	* @param imgStr
	* @return 输入流
	*/
	private static InputStream generateImage(String imgStr)throws Exception {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null){
			// 图像数据为空
			return null;
		}
		
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			
			return new ByteArrayInputStream(b);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void close(InputStream is){
		
		try {
			if(is != null){
				is.close();
			}
		} catch (IOException e) {
			logger.error("fail to close inputstream!",e);
		}
	}
	/**
	 * 裁剪图片
	 * @param key 阿里云图片的key
	 * @param startX 
	 * @param startY
	 * @param width
	 * @param height
	 */
	public static boolean cutImg(String key, int startX,int startY,int width,int height){
    	
    	if(key==null || "".equals(key.trim())){
    		logger.warn("key can not be empty!");
    		return false;
    	}
    	
    	InputStream is = AliImgServer.getImg(key);
    	
    	if(is == null){
    		logger.warn("img with key="+key+" not exist!");
    		return false;
    	}
    	
    	try {
			BufferedImage bufImg = ImageIO.read(is);
			/*调整宽和高*/
			if(startX+width>bufImg.getWidth()){
				width = bufImg.getWidth()-startX;
			}
			if(startY+height>bufImg.getHeight()){
				height = bufImg.getHeight()-startY;
			}
			
			BufferedImage subImg = bufImg.getSubimage(startX, startY, width, height);
			
//			ImageIO.write(subImg,"jpg",new File("e://test/3.jpg"));
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(subImg, "jpg", os);
			InputStream imgIs = new ByteArrayInputStream(os.toByteArray());
			AliImgServer.putImg(key, imgIs);
		} catch (IOException e) {
			logger.error("fail to cut img!",e);
			return false;
		}
    	
    	return true;
    }

	public static void main(String args[]) {

//		System.out.println(ImgUtil.getFileType("122.tg.jpg"));
//		System.out.println(ImgUtil.getFileType("122.tg.jpgett"));
		System.out.println(ImgUtil.getImgUrl("http://img.ilaipai.com/122345@.jpg","webp",100));
//		System.out.println(ImgUtil.cutImg("test", 1000, 0, 3000, 2000));
//		BASE64Encoder encoder = new BASE64Encoder();
//		BASE64Decoder decoder = new BASE64Decoder();
//		try {
//			String encode = encoder.encode("123456中国".getBytes("utf-8"));
//			System.out.println(encode);
//			System.out.println(new String(decoder.decodeBuffer(encode),"utf-8"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
