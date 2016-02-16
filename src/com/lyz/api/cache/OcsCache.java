package com.lyz.api.cache;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;

import com.lyz.api.config.SystemConfig;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.ConnectionFactoryBuilder.Protocol;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

/**
 * 阿里云缓存
 * @author luyongzhao
 *
 */
public class OcsCache implements ICache {
	
	private static final String host = "ef8c18bbf56e4dc1.m.cnbjalicm12pub001.ocs.aliyuncs.com";//控制台上的“内网地址”
	private static final String port ="11211"; //默认端口 11211，不用改
	private static final String username = "ef8c18bbf56e4dc1";//控制台上的“访问账号”
	private static final String password = "Laipai2015";//邮件中提供的“密码”
	
	private  MemcachedClient cache = null;
	
	private static final Logger logger = Logger.getLogger(OcsCache.class);
	
	private static final AtomicReference<OcsCache> instance = new AtomicReference<OcsCache>();
	
	public OcsCache(){
		
		 AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"}, new PlainCallbackHandler(username, password));

         try {
			cache = new MemcachedClient(
			                    new ConnectionFactoryBuilder().setProtocol(Protocol.BINARY)
			         .setAuthDescriptor(ad)
			         .build(),
			         AddrUtil.getAddresses(host + ":" + port));
		} catch (IOException e) {
			logger.error("fail to connect to ocs!",e);
		}
	}
	
	public static OcsCache getInstance(){
		
		if(instance.get() == null){
			instance.compareAndSet(null, new OcsCache());
		}
		
		return instance.get();
	}

	@Override
	public String getData(String key) {
		
		if(key == null || "".equals(key.trim())){
			return null;
		}
		try {
			Object obj = cache.get(getKey(key));
			if(obj == null){
				return null;
			}
			return (String)obj;
		} catch (Exception e) {
			logger.error("fail to get data from cache,key="+key);
			return null;
		}
	}
	
	private String getKey(String key){
		
		return SystemConfig.env+":"+key;
	}

	@Override
	public void setData(String key, String data, int exipired) {
		
		try {
			cache.set(getKey(key), exipired, data);
		} catch (Exception e) {
			logger.error("fail to set data into cache, key="+key);
		}
	}
	
	public void setObjectData(String key, Object data, int exipired){
		cache.set(getKey(key), exipired, data);
	}
	
	public Object getObjectData(String key){
		
		if(key == null || "".equals(key.trim())){
			return null;
		}
		return cache.get(getKey(key));
	}

}
