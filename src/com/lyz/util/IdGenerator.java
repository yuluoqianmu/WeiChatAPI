package com.lyz.util;

import java.security.InvalidParameterException;

import com.laipai.util.SleepUtil;

/**
 * id生成器,每一种类型的数据需要单独一个生成器实例，以保证并发高效性
 * 同一种类型数据来自不同的地方生成可以指定生成来源，每一种id只能指定最多10中来源
 * id生成由三部分组成：时间戳+类型+来源
 * @author luzi
 *
 */
public class IdGenerator {
	
	/*类型*/
	private int type;
	/*来源*/
	private int source;
	/**
	 * 
	 * @param type 类型 0-99
	 * @param source 来源,用于多个系统生成相同类型id使用，通过该字段不需要独立的id生成系统 0-9
	 */
	public IdGenerator(int type, int source)throws InvalidParameterException{
		
		if(type<0 || type>99){
			throw new InvalidParameterException("type should be in [0,99]");
		}
		if(source<0 || source>9){
			throw new InvalidParameterException("source should be in [0,9]");
		}
		
		this.type = type;
		this.source = source;
	}

	/**
	 *生成id
	 */
	public synchronized long generateId(){
		
		long now = System.currentTimeMillis();
		/*睡眠1ms，防止出现重复数据*/
		SleepUtil.sleepForCertainTime(1);
		
		return now*1000+this.type*10+this.source;
	}
	
	
	public static void main(String args[]){
		
		IdGenerator userGenerator = new IdGenerator(1, 0);
		
		for(int i=0; i<10000; i++){
			System.out.println(userGenerator.generateId());
		}
	}
}
