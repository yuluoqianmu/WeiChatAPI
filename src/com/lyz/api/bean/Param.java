package com.lyz.api.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 参数列表，用于获取上一个请求的返回参数，向下一个请求传递参数
 * @author luyongzhao
 *
 */
public class Param {

	private Map<String,Object> param = null;
	
	public Param(){
		param = new HashMap<String, Object>();
	}
	
	public void setParameter(String key, Object value){
		param.put(key, value);
	}
	
	public Object getParameter(String key){
		return param.get(key);
	}
	
	public void remove(String key){
		
		param.remove(key);
	}
	
	public Set<String> getKeySets(){
		
		return param.keySet();
	}
	
	public void clear(){
		param.clear();
	}
	
	

}
