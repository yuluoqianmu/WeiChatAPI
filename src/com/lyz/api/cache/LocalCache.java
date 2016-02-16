package com.lyz.api.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 本地缓存
 * @author luzi
 *
 */
public class LocalCache implements ICache {
	
	private static  CacheManager cacheManger = new CacheManager();
	
	private Cache cache = null;
	
	private LocalCache(){
		
		cache = cacheManger.getCache("default");
	}

	public String getData(String key) {
		
		if(key==null || "".equals(key.trim())){
			return null;
		}
		
		Element ele = cache.get(key);
		if(ele == null){
			return null;
		}
		
		return (String)ele.getObjectValue();
	}
	/**
	 * exipired不起作用参考ehcahce.xml中的设置
	 */
	public void setData(String key, String data, int exipired) {
		
		/*如果key val都为空，则不入缓存*/
		if(key==null || "".equals(key.trim())){
			return;
		}
		
		if(data==null || "".equals(data.trim())){
			return;
		}
		
		cache.put(new Element(key, data));
	}
	
	public static void main(String args[]){
		
		LocalCache cache = new LocalCache();
		
		cache.setData("test", "test", 0);
		
		System.out.println(cache.getData("test"));
	}

}
