package com.laipai.util;

import java.util.ArrayList;
import java.util.List;

public class ResTypeUtil {
	/**
	 * 是否为mp4码流
	 * @param type
	 * @return
	 */
	public static boolean isMp4(int type){
		
		if(type==1 || type==2 || type==32){
			return true;
		}
		
		return false;
	}
	/**
	 * 转换TS码流地址
	 * @param res
	 * @return
	 */
	public static String getIqiyiTsRes(String res){
		
		if(res.contains("meta.video.qiyi.com")){
			return res.replace("meta.video.qiyi.com", "metal.video.qiyi.com");
		}else if(res.contains("meta1.video.qiyi.com")){
			return res.replace("meta1.video.qiyi.com", "metal.video.qiyi.com");
		}
		
		return res;		
	}
	
	public static String getIqiyiMp4Url(String srcUrl){
		
		if(srcUrl==null || "".equals(srcUrl)){
			return null;
		}
		
		int value = ((int)(System.currentTimeMillis()/1000)) ^ 1720301010;
		
		if(srcUrl.contains("?")){
			return srcUrl+"&v="+value;
		}else{
			return srcUrl+"?v="+value;
		}
		
	}
	
	/**
	 * 获取客户端需要的码流列表，从大到小排列
	 * @param rt
	 * @return
	 */
	public static List<Integer> getIqiyiRtList(int rt){
		
		List<Integer> rtList = new ArrayList<Integer>();
		
		/**
		 * 如果没有指定值，则按最小兼容方式
		 */
		if(rt <= 0){
			rtList.add(1);
			rtList.add(4);
			rtList.add(8);
//			rtList.add(32);
			return rtList;
		}
		/*4000k TS*/
		if((rt&512) > 0){
			rtList.add(512);
		}
		/*2000k TS*/
		if((rt&256) > 0){
			rtList.add(256);
		}
		/*1000k TS*/
		if((rt&16) > 0){
			rtList.add(16);
		}
		/*600k TS*/
		if((rt&8) > 0){
			rtList.add(8);
		}
		/*400k TS*/
		if((rt&4) > 0){
			rtList.add(4);
		}
		/*600k mp4*/
		if((rt&2) > 0){
			rtList.add(2);
		}
		/*400k mp4*/
		if((rt&32) > 0){
			rtList.add(32);
		}
		/*200k mp4*/
		if((rt&1) > 0){
			rtList.add(1);
		}
		/*180k TS*/
		if((rt&128) > 0){
			rtList.add(128);
		}
		
		return rtList;
		
	}
}
