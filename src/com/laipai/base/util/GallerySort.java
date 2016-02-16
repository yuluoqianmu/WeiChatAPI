package com.laipai.base.util;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GallerySort {
	
	public static double getHotsource(int comment,int view,int like,Timestamp createTime){
		
		
		double parameterOne=view*2+comment*5+ like*3;
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  //此处会抛异常
		  Date date;
		  double result = 0;
		try {
			date = sdf.parse("2015-01-01 23:59:59");
			 long parameTime = date.getTime()/604800 ;
			 long create= createTime.getTime();
				
				double resultTime= create-parameTime;
				
				 result=parameterOne+resultTime;	
				
		} catch (ParseException e) {
			e.printStackTrace();
		}
		  //获取毫秒数
			
		 return result;
	}
	
	public static void main(String[] args) {
		Date date= DateUtils.stringToDate("2015-01-22 17:59:59");
		Timestamp time= new Timestamp(date.getTime());
		System.out.println(getHotsource(0,500,0,time));
	}

}
