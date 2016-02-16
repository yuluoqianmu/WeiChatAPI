package com.laipai.base.util;

import com.laipai.galaryManInfo.dto.SimpleImage;
import com.laipai.laiPaiClubInfo.dto.LpClubBean;
import com.laipai.userManInfo.dto.LpUserBean;

public class LaipaiConstants {

	//系统所有图片上传物理路径
	public static String UPLOAD_ABSOLUTE_IMG = "/data/laipai/upload";
	//系统所有图片展示虚拟路径
	public static String UPLOAD_VIRTUAL_IMG ="/upload";
	
	public static final String serverUrl = "http://api.ilaipai.com";
	
	public static final String imgUrl = "http://img.ilaipai.com/";
	
	public static final String UPLOAD_SUBJECT_IMG = "lpSubjectImg";
	/*用户头像保存的绝对路径*/
	public static final String UPLOAD_USER_PATH = LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpUserBean.LP_USER_IMGURL;
	/*专题id*/
	public static final String UPLOAD_SUBJECT_PATH = LaipaiConstants.UPLOAD_ABSOLUTE_IMG+ "/lpSubjectImg";
	/*来拍摄*/
	public static final String UPLOAD_CLUB_PATH = LaipaiConstants.UPLOAD_ABSOLUTE_IMG+LpClubBean.LP_CLUB_IMGURL;
	/*作品集*/
	public static final String UPLOAD_GALARY_PATH = LaipaiConstants.UPLOAD_ABSOLUTE_IMG+SimpleImage.LP_GALLERY_IMGURL;
	/*作品集原图*/
	public static final String UPLOAD_GALARY_SOURCE_PATH = LaipaiConstants.UPLOAD_ABSOLUTE_IMG+SimpleImage.LP_GALLERY_IMGURL;
	
	public static final String upload = serverUrl+LaipaiConstants.UPLOAD_VIRTUAL_IMG;
	
	//本地测试http接口地址
	public static String HTTP_URL = "http://localhost:8080/LaiPai/laiPaiAPPServlet.htm";
	//测试环境http接口地址
	public static String HTTP_123_TEST = "http://123.56.102.223:8080/LaiPai/laiPaiAPPServlet.htm";
//	public static String HTTP_123_ZS = "http://123.56.102.223/LaiPaizs/laiPaiAPPServlet.htm";
	
	private static int verifyCodeLength = 4;
	
	public static int getVerifyCodeLength() {
		return verifyCodeLength;
	}

	public static void setVerifyCodeLength(int verifyCodeLength) {
		LaipaiConstants.verifyCodeLength = verifyCodeLength;
	}

	public static String getUPLOAD_ABSOLUTE_IMG() {
		return UPLOAD_ABSOLUTE_IMG;
	}

	public static void setUPLOAD_ABSOLUTE_IMG(String uPLOAD_ABSOLUTE_IMG) {
		UPLOAD_ABSOLUTE_IMG = uPLOAD_ABSOLUTE_IMG;
	}

	public static String getUPLOAD_VIRTUAL_IMG() {
		return UPLOAD_VIRTUAL_IMG;
	}

	public static void setUPLOAD_VIRTUAL_IMG(String uPLOAD_VIRTUAL_IMG) {
		UPLOAD_VIRTUAL_IMG = uPLOAD_VIRTUAL_IMG;
	}
	
	
	
}
