package com.laipai.app.common;

import java.io.IOException;
import java.io.Writer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * json工具类
 * 
 * @author ts
 */
public class JSONTools {

	/**    
	 * 返回简略的request结果
	 * 
	 * @param result
	 * @param msg
	 * @return
	 */
	public static JSONObject newAPIResult(int result, String msg) {
		JSONObject jsonObject = new JSONObject();
		if (msg != null) {
			jsonObject.put("message", msg);
		}
		jsonObject.put("result", result);
		return jsonObject;
	}

	public static String newAPIResultToString(int result, String msg) {
		JSONObject jsonObject = new JSONObject();
		if (msg != null) {
			jsonObject.put("message", msg);
		}
		jsonObject.put("result", result);
		return jsonObject.toString();
	}

	public static void outResult(Writer out, int result, String msg) {
		JSONObject jsonObject = new JSONObject();
		if (msg != null) {
			jsonObject.put("message", msg);
		}
		jsonObject.put("result", result);
		String str = jsonObject.toString();

		try {
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JSONArray getJSONArray(JSONObject obj, String key, JSONArray... def) {
		JSONArray result = null;
		Object value = obj.get(key);
		if (value != null) {
			try {
				result = (JSONArray) value;
			} catch (Exception e) {
			}
		}
		if (result == null && def != null && def.length == 1) {
			result = def[0];
		}
		return result;
	}

	public static String getString(JSONObject obj, String key, String... def) {
		String result = null;
		Object value = obj.get(key);
		if (value != null) {
			try {
				result = value.toString();
			} catch (Exception e) {
			}
		}
		if (result == null && def != null && def.length == 1) {
			result = def[0];
		}
		return result;
	}

	/**
	 * 得到integer类型
	 * 
	 * @param obj
	 * @param key
	 * @param def
	 * @return
	 */
	public static Integer getInteger(JSONObject obj, String key, Integer... def) {
		Integer result = null;
		Object value = obj.get(key);
		if (value != null) {
			try {
				result = Integer.parseInt(value.toString());
			} catch (Exception e) {
			}
		}
		if (result == null && def != null && def.length == 1) {
			result = def[0];
		}
		return result;
	}

	public static Long getLong(JSONObject obj, String key, Long... def) {
		Long result = null;
		Object value = obj.get(key);
		if (value != null) {
			try {
				result = Long.parseLong(value.toString());
			} catch (Exception e) {
			}
		}
		if (result == null && def != null && def.length == 1) {
			result = def[0];
		}
		return result;
	}
	
	public static Double getDouble(JSONObject obj, String key, Double... def) {
		Double result = null;
		Object value = obj.get(key);
		if (value != null) {
			try {
				result=Double.parseDouble(value.toString());
			} catch (Exception e) {
			}
		}
		if (result == null && def != null && def.length == 1) {
			result = def[0];
		}
		return result;
	}

}
