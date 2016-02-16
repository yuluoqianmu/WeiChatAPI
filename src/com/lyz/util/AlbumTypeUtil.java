package com.lyz.util;

import java.util.HashSet;
import java.util.Set;

public class AlbumTypeUtil {
	/*电影类单集*/
	public static final int TYPE_SINGLE = 0;
	/*电视剧类分集*/
	public static final int TYPE_SERIAL = 1;
	/*综艺类分期*/
	public static final int TYPE_TIME = 2;
	/*未知*/
	public static final int TYPE_UNKNOWN = -1;
	
	private static final Set<Integer> singleSet = new HashSet<Integer>();
	
	private static final Set<Integer> serialSet = new HashSet<Integer>();
	
	private static final Set<Integer> timeSet = new HashSet<Integer>();
	
	static{
		/*电影*/
		singleSet.add(1);
		/*推荐*/
		singleSet.add(1000);
		/*电视剧、动漫*/
		serialSet.add(2);
		serialSet.add(4);
		/*综艺*/
		timeSet.add(6);
	}
	
//	public static boolean isSingleTv(int cnn){
//		
//		return singleSet.contains(cnn);
//	}
//	
//	public static boolean isSerialTv(int cnn){
//		return serialSet.contains(cnn);
//	}
//	
//	public static boolean isTimeTv(int cnn){
//		return timeSet.contains(cnn);
//	}
	/**
	 * 根据频道获取显示类型
	 * @param cnn
	 * @return
	 */
//	public static int getWType(int cnn){
//		
//		if(isSingleTv(cnn)){
//			return TYPE_SINGLE;
//		}else if(isSerialTv(cnn)){
//			return TYPE_SERIAL;
//		}else if(isTimeTv(cnn)){
//			return TYPE_TIME;
//		}else{
//			return TYPE_UNKNOWN;
//		}
//	}
}
