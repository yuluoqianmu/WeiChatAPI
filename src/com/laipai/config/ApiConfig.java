package com.laipai.config;


import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;

import com.laipai.util.BaseTypeUtil;
import com.laipai.util.PropertiesUtil;


/**
 * 单例模式，配置文件的路径决定于getInstance(String filePath)和gerInstance()哪个先被调用
 * ，如果需要用户指定路径，在服务器启动时首先调用getInstance(String filePath)
 * @author iqiyi
 *
 */
public class ApiConfig {
	
	private static final Logger logger = Logger.getLogger(ApiConfig.class);
	
	private PropertiesUtil proUtil = null;
	
	private static AtomicReference<ApiConfig> serverConfig = new AtomicReference<ApiConfig>();
	private static final String defaultFilePath = ApiConfig.class
	            .getClassLoader().getResource("serverConfig.properties").getPath();
	private String filePath = null;
	
	private ApiConfig(String filePath){
		
		if(filePath == null){
			this.filePath = defaultFilePath;
			logger.warn("use default server configuration file path:"+defaultFilePath);
		}else{
			this.filePath = filePath;
		}
		
		proUtil = new PropertiesUtil(this.filePath);
	}
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static ApiConfig getInstance(String filePath){
		
		if(serverConfig.get() == null){
			serverConfig.compareAndSet(null, new ApiConfig(filePath));
		}
		
		return serverConfig.get();
	}

	public static ApiConfig getInstance(){
		
		return getInstance(null);
	}
	
	private String getErrorMsg(String param){
		return param + " in serverConfig does not exist!"; 
	}
	private String getErrorMsg(String param,String defaultValue){
		return param + " in serverConfig does not exist!default value is "+defaultValue; 
	}
	
	public String getStringVal(String key){
		
		String val = proUtil.getProperty(key);
		if(val==null || "".equals(val.trim())){
			logger.error(getErrorMsg(key));
			return null;
		}
		return val.trim();
	}
	public String getStringVal(String key,String defaultVal){
		
		String val = proUtil.getProperty(key);
		if(val==null || "".equals(val.trim())){
			logger.error(getErrorMsg(key,defaultVal));
			return null;
		}
		return val.trim();
	}
	
	public String[] getConfigTags(){
		String sTag = getStringVal("configTags");
		if(sTag == null){
			logger.error(getErrorMsg("configTags"));
			return new String[0];
		}
		return sTag.split(",");
	}

	/**
	 * 根据tag决定文件的存储路径
	 * @param tag
	 * @return
	 */
	public String getFileDir(String tag){

		return getStringVal(tag+"FileDir");
	}
	
	public String getDateFormat(String tag){
		
		return getStringVal(tag+"DateFormat");
	}
	
	public String getFileStart(String tag){

		return getStringVal(tag+"FileStart");
	}
	public String getFileEndWith(String tag){

		return getStringVal(tag+"FileEndWith");
	}
	public String getFileSplit(String tag){

		return getStringVal(tag+"FileSplit");
	}
	
	public long getSplitInterval(String tag){
		
		String sInterval = getStringVal(tag+"SplitInterval");

		return BaseTypeUtil.getMiliSeconds(sInterval);
	}
	
	public int getSpliteIntevalVal(String tag){
		
		String sInterval = getStringVal(tag+"SplitInterval");
		
		return Integer.parseInt(sInterval.substring(0,sInterval.length()-1));
	}
	
	public String getSpliteIntervalUnit(String tag){
		
		String sInterval = getStringVal(tag+"SplitInterval");
		return sInterval.substring(sInterval.length()-1,sInterval.length());
	}
//	
//	public ICache getCacheInstance(){
//		
//		String clsStr = getStringVal("cacheCls");
//		
//		if(clsStr == null){
//			logger.error(getErrorMsg("cacheCls"));
//			return null;
//		}
//		
//		return (ICache)ClassUtil.newInstance(clsStr, null);
//		
//	}
	/**
	 * 获取缓存时间
	 * @param part
	 * @return
	 */
	public int getCacheTime(String part){
		
		String sTime = getStringVal("cache:"+part);
		
		return BaseTypeUtil.getInteger(sTime, 0);
	}
	
	/**
	 * 视图页缓存时间：秒
	 * @return
	 */
	public int getViewCacheTime(){
		
		String sInterval = getStringVal("viewCacheTime");
		
		return Integer.parseInt(sInterval,0);
	}	
	/**
	 * 列表页缓存时间：秒
	 * @return
	 */
	public int getListCacheTime(){
		
		String sInterval = getStringVal("listCacheTime");
		
		return Integer.parseInt(sInterval,0);
	}	
	/**
	 * 详情页缓存时间：秒
	 * @return
	 */
	public int getDetailCacheTime(){
		
		String sInterval = getStringVal("detailCacheTime");
		
		return Integer.parseInt(sInterval,0);
	}	
	/**
	 * 获取文件默认编码
	 * @return
	 */
	public String getDefaultEncode(){
		
		String encode = getStringVal("defaultEncode");
		
		if(encode == null || "".equals(encode.trim())){
			return "utf-8";
		}
		
		return encode;
	}
	/**
	 * 图片存放Dns
	 * @return
	 */
	public String getImgDns(){
		
		return getStringVal("imgDns");
	}
	/**
	 * 图片存放文件夹
	 * @return
	 */
	public String getImgForder(){
		
		return getStringVal("imgForder");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApiConfig config = ApiConfig.getInstance("e://config.txt");
		System.out.println("tag==="+config.getFileDir("vvlog"));
		

	}

}
