package com.laipai.base.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
	
	 /**
     * 字符串形式日期格式
     */
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");

    /**
     * 字符串形式时间格式
     */
    private static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    
    /**
     * 字符串形式时间格式
     */
    private static SimpleDateFormat smstimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	//将日期格式，转换成String类型的文件夹
	public static String dateToStringPath(Date date) {
		String sDatePath = new SimpleDateFormat("/yyyy/MM/dd").format(date);
		return sDatePath;
	}
	
	//将日期格式，转换成String类型（格式：yyyy-MM-dd HH:mm:ss)
	public static String dateToString(Date date) {
		String sDatePath = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return sDatePath;
	}
	
	//将日期格式，转换成String类型（格式：yyyyMMddHHmmss)
	public static String dateToStringExcel(Date date) {
		String sDatePath = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
		return sDatePath;
	}

	//将String类型的日期格式，转换成Date类型
	public static Date stringToDate(String sDate){
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	//将String类型的日期格式，转换成Timestamp类型
	public static Timestamp stringToTimestamp (String timestampStr, String format){
	    if (timestampStr == null || timestampStr.trim ().equals ("")) {
	        return null;
	    }
	    if(StringUtils.isEmpty(format)){
	    	format = "yyyy-MM-dd HH:mm:ss";
	    }
	    SimpleDateFormat dateFormat = new SimpleDateFormat (format);
	    try {
	        Date date = dateFormat.parse (timestampStr);
	        return new Timestamp (date.getTime ());
	    } catch (Exception e) {
	          e.printStackTrace();
	    }
	    return null;
	}
	
    /**
     * 得到当前字符串形式日期 格式：yyyy年mm月dd日
     * 
     * @return String 当前字符串形式日期
     */
    public static String getCurrDate()
    {
        return dateFormat.format(new Date());
    }

    /**
     * 得到当前字符串形式时间 格式：yyyy年mm月dd日 hh:MM:ss
     * 
     * @return String 当前字符串形式时间
     */
    public static String getCurrTime()
    {
        return datetimeFormat.format(new Date());
    }
    
    /**
     * 得到当前字符串形式时间 格式：yyyy年mm月dd日 hh:MM:ss
     * 
     * @return String 当前字符串形式时间
     */
    public static String getSMSCurrTime()
    {
        return smstimeFormat.format(new Date());
    }
    
    /**
     * 得到当前字符串形式时间 格式：yyyy年mm月dd日 hh:MM:ss
     * 
     * @return String 当前时间-30分钟字符串形式时间
     */
    public static String getSMSTime()
    {
    	Date curDate = new Date();
    	
    	//得到当前时间减去30分钟后的时间
    	long smsTime = curDate.getTime()-30*60*1000;
    	
    	return  datetimeFormat.format(new Date(smsTime));
    }
	
	/**
	 * 取得今天是周几的信息
	 */
	public static int getCurrentDay_Week(){
		Calendar cr = Calendar.getInstance();
		return cr.get(cr.DAY_OF_WEEK);	
	}
	
	/**
	 * date类型转换成Timestamp类型
	 * */
	public static Timestamp dateToTimestamp(Date date){
		 	if (date == null) {
		 		date = new Date();
		    }
		    try {
		    	Timestamp dateTime = new Timestamp(date.getTime());//Timestamp类型,timeDate.getTime()返回一个long型
		        return dateTime;
		    } catch (Exception e) {
		          e.printStackTrace();
		    }
		    return null;
	}
	
	/**
	 * 返回指定格式的日期所对应的字符串格式
	 * @param date 指定的日期
	 * @param format 日期格式
	 * @return  字符串
	 */
	public static String parseToString(Date date, String format) {
		if(date==null){
			return "";
		}
		if (format != null) {
			return (new SimpleDateFormat(format)).format(date);
		} else {
			return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
					.format(date);
		}
	}
	
	public static String parseTimestampToString(Timestamp ts, String format) {
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(StringUtils.isNotEmpty(format)){
        	sdf = new SimpleDateFormat(format);
        }
        try {   
            //方法一   
            tsStr = sdf.format(ts);   
        } catch (Exception e) {   
            e.printStackTrace();   
        }  
        return tsStr;
	}
	
}
