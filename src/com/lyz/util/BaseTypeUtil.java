package com.lyz.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class BaseTypeUtil {

	/**
	 * 将字符串id转换为整形数字，id不存在负值
	 * @param sId
	 * @return 整形数值的id，如果发生异常返回-1
	 */
	public static int convertId(String sId){
		
		if(sId == null || "".equals(sId.trim())){
			return -1;
		}
		
		int id = 0;
		try {
			id = Integer.parseInt(sId);
		} catch (NumberFormatException e) {
			return -1;
		}
		
		return id;
		
	}
	
	public static long getTime(String time, String format){
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(time);
			return date.getTime();
			
		} catch (ParseException e) {
			return -1;
		}
		
	}
	
	public static String getStrDateTime(long time, String format){
		
		String date = null;
		
		if(time<0 || format==null){
			return null;
		}
		
		
		try {
			date = new SimpleDateFormat(format).format(new Date(time));
		} catch (Exception e) {
			return null;
		}
		
		return date;
	}
	
	public static String getTodayTime(String format){
		String date = null;
		try {
			date = new SimpleDateFormat(format).format(new Date());
		} catch (Exception e) {
			return null;
		}
		
		return date;
	}
	/**
	 * 获取在起始字符串和结束字符串之间的字符串
	 * @param line 原始字符串
	 * @param startStr
	 * @param endStr
	 * @return 返回字符串，没有返回null
	 */ 
	public static String getSubString(String line, String startStr, String endStr){
		
		if(line == null || startStr==null || endStr==null){
			return null;
		}
		
		int length = startStr.length();
		int start = line.indexOf(startStr);
		int end = line.indexOf(endStr,start+length);
		
		if(end<start){
			return null;
		}
		return line.substring(start+length,end);
		
	}
	
	public static String getSubString(StringBuffer line, String startStr, String endStr, int startPosition){
		
		if(line == null || startStr==null || endStr==null){
			return null;
		}
		
		int length = startStr.length();
		int start = line.indexOf(startStr,startPosition);
		int end = line.indexOf(endStr,start+length);
		
		if(start<0 || end<start){
			return null;
		}
		return line.substring(start+length,end);
		
	}
	
	/**
	 * 从position指定的位置开始搜索，依次判断是否包含数组中指定字符串，包含，则返回最后一个字符串的位置，不包含则返回-1
	 * @param buffer
	 * @param keywords
	 * @param position
	 * @return
	 */
	public static int contains(final StringBuffer buffer, final String[] keywords, int position){
		
		if(keywords == null || keywords.length==0){
			return -1;
		}
		
		for(int i=0; i<keywords.length; i++){
			position = buffer.indexOf(keywords[i], position);
			if(position<0){
				return -1;
			}
		}
		
		return position;
	}
	
	/**
	 * 将unicode编码的字符串转换成实际对应的字符串
	 * @param unicodeString
	 * @return
	 */
	public static String decodeUnicodeString(String unicodeString){
		
		if(unicodeString == null){
			return null;
		}
		
		String line = null;
		BufferedReader bufferedReader = null;
		
		bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(unicodeString.getBytes())));
		try {
			line = bufferedReader.readLine();
			if(bufferedReader != null){
				bufferedReader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return line;
		
		
	}
	
	/**
	 * 获取正整数值
	 * @param intVal
	 * @return 出现错误，返回-1
	 */
	public static int getInteger(String intVal){
		try {
			return Integer.parseInt(intVal);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	public static int getInteger(String intVal,int defaultVal){
		try {
			return Integer.parseInt(intVal);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
	}
	
	public static float getFloat(String floatVal, float defaultVal){
		
		if(floatVal == null || "".equals(floatVal)){
			return defaultVal;
		}
		
		try {
			return Float.parseFloat(floatVal);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
		
	}
	
	public static double getDouble(String floatVal, double defaultVal){
		
		if(floatVal == null || "".equals(floatVal)){
			return defaultVal;
		}
		
		try {
			return Double.parseDouble(floatVal);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
		
	}
	
	public static boolean getBoolean(String booleanVal, boolean defaultVal){
		
		if(booleanVal == null || "".equals(booleanVal)){
			return defaultVal;
		}
		
		try {
			return Boolean.parseBoolean(booleanVal);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
		
	}
	
	public static long getLong(String longVal,long defaultVal){
		
		if(longVal==null || "".equals(longVal.trim())){
			return defaultVal;
		}
		try {
			return Long.parseLong(longVal);
		} catch (NumberFormatException e) {
			return defaultVal;
		}
	}
	
	
	/**
	 * 获取毫秒时间
	 * @param time 以d(ay) h(our) m(inute) s(econd)结尾的时间表示方式，如1h表示一小时 
	 * @return 出现错误返回-1
	 */
	public static long getMiliSeconds(String time){
		long interval = 0;
		interval = getInteger(time.substring(0, time.length()-1));
		if(interval < 0){
			return -1;
		}
		if(time.endsWith("d")){
			return interval*24*60*60*1000;
		}else if(time.endsWith("h")){
			return interval*60*60*1000;
		}else if(time.endsWith("m")){
			return interval*60*1000;
		}else if(time.endsWith("s")){
			return interval*1000;
		}
		
		return -1;
	}
	
	

	public static void main(String args[]){
//		System.out.println(BaseTypeUtil.contains(new StringBuffer("abcdefg"), new String[]{"ab","cd"}, 0));
//		System.out.println(new Date().getHours());
//		System.out.println(BaseTypeUtil.getStrDateTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
	    Calendar cd = Calendar.getInstance();  
	    SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss 'GMT'", Locale.CHINA);  
	    sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT  
	        String str = sdf.format(cd.getTime());  
	           System.out.println(str);  
	}
}
