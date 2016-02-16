package com.lyz.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;


/**
 * 来拍id生成器
 * @author luzi
 *
 */
public class LaiPaiEntityIdGenerator {
	
	private static final Logger logger = Logger.getLogger(LaiPaiEntityIdGenerator.class);
	
	public static final int source = 1;
	/*用户*/
	public static final int typeUser = 0;
	/*图片*/
	public static final int typePic = 1;
	/*作品集*/
	public static final int typeGalary = 2;
	/*专题*/
	public static final int typeSubject = 3;
	/*评论*/
	public static final int typeComment = 30;
	/*来拍摄文章*/
	public static final int typeClub = 4;
	/*新晋摄影师介绍*/
	public static final int typeNewIntroduce = 5;	
	/*城市*/
	public static final int typeCity = 6;
	/*拍摄类型*/
	public static final int typeShoot = 7;
	/*服务*/
	public static final int typeService = 8;
	/*服务详情*/
	public static final int typeServiceDetail = 9;
	/*订单*/
	public static final int typeOrder = 10;
	
	
	public static Map<Integer,IdGenerator> type2Gen = new HashMap<Integer, IdGenerator>();
	
	static{		
		type2Gen.put(typeUser, new IdGenerator(typeUser, source));
		type2Gen.put(typePic, new IdGenerator(typePic, source));
		type2Gen.put(typeGalary, new IdGenerator(typeGalary, source));
		type2Gen.put(typeSubject, new IdGenerator(typeSubject, source));
		type2Gen.put(typeCity, new IdGenerator(typeCity, source));
		type2Gen.put(typeShoot, new IdGenerator(typeShoot, source));
		type2Gen.put(typeComment, new IdGenerator(typeComment, source));
		type2Gen.put(typeClub, new IdGenerator(typeClub, source));
		type2Gen.put(typeNewIntroduce, new IdGenerator(typeNewIntroduce, source));
		type2Gen.put(typeService, new IdGenerator(typeService, source));
		type2Gen.put(typeServiceDetail, new IdGenerator(typeServiceDetail, source));
		type2Gen.put(typeOrder, new IdGenerator(typeOrder, source));
	}
	
	/**
	 * 获取id，失败返回0
	 * @param type
	 * @return 失败返回0，成功返回一个长整形数据
	 */
	public static long generatorId(int type){
		
		IdGenerator gen = type2Gen.get(type);
		
		if(gen == null){
			logger.error("unknow type:"+type);
			return 0;
		}
		
		return gen.generateId();
	}
	/**
	 * 从实体id获取时间戳
	 * @param entityId
	 * @param format
	 * @return
	 */
	public static String getTime(long entityId, String format){
		
		entityId = entityId/1000;
		
		return BaseTypeUtil.getStrDateTime(entityId, format);
	}
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String args[]){
		
//		for(int i=0; i<1000; i++){
//			
//			for(int type=10; type<11; type++){
//				
//				System.out.println(LaiPaiEntityIdGenerator.generatorId(type));
//			}
//		}
		
		System.out.println(LaiPaiEntityIdGenerator.generatorId(8));
	}
}
