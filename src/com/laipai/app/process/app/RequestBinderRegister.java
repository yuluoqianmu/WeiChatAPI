package com.laipai.app.process.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.laipai.app.process.app.binder.UserAppRequestBinderRegister;


/**
 * 请求对像绑定
 * 
 * @author ts
 * @date 2014-11-13
 */
public final class RequestBinderRegister {
	private static final Map<String, Class<?>> REQUEST_MESSAGE_TYPE = new HashMap<String, Class<?>>();// 请求进来的绑定类型

	public static Class<?> getClass(Integer appType, Integer requestId) {
		return REQUEST_MESSAGE_TYPE.get(appType + "_" + requestId);
	}

	static {

		Map<Integer, Class<?>> userMap = UserAppRequestBinderRegister.REQUEST_MESSAGE_TYPE;

		setMapValue(0, userMap);
	}

	private static void setMapValue(int type, Map<Integer, Class<?>> map) {
		Set<Map.Entry<Integer, Class<?>>> set = map.entrySet();
		for (Map.Entry<Integer, Class<?>> e : set) {
			String key = type + "_" + e.getKey();
			if (REQUEST_MESSAGE_TYPE.get(key) != null) throw new RuntimeException("executor KEY " + e.getKey() + " duplicate ");
			REQUEST_MESSAGE_TYPE.put(key, e.getValue());
		}
	}

}
