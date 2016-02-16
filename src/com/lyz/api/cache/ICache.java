
package com.lyz.api.cache;
/**
 * 基本缓存操作类
 * 实现该接口的类必须包含不带参数的构造方法，并且构造方法是public的
 * 不同缓存实现该类的操作方式会有所不同，比如，
 * 本地缓存：由于受到本地内存的限制，其实现方式可能是限制本地内存大小，限制缓存数量，而不能采用过期时间
 * redis、couchbase缓存：考虑过期时间
 * @author luzi
 *
 */
public interface ICache {
	/**
	 * 永不过期
	 */
	public static final int EXPIRED_NEVER = 0;
	
	public static final int EXPIRED_ONE_SECOND = 1;
	/**
	 * 一分钟
	 */
	public static final int EXPIRED_ONE_MINUTE = 60;
	/**
	 * 一小时
	 */
	public static final int EXPIRED_ONE_HOUR = 60*60;
	/**
	 * 一天
	 */
	public static final int EXPIRED_ONE_DAY = 60*60*24;
	
	
	/**
	 * 缓存数据
	 * @param key
	 * @param data
	 * @param exipired 单位秒，缓存时长
	 */
	public void setData(String key, String data, int exipired);
	/**
	 * 获取缓存数据
	 * @param key
	 * @return
	 */
	public String getData(String key);
}
