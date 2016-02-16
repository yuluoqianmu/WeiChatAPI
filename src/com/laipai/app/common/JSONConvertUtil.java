package com.laipai.app.common;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.laipai.app.annotation.JSONNoteName;
import com.laipai.app.annotation.ListObjectType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * json对像转object,对像支持JSONFieldKey,JSONArrayType annotation
 * 
 * @author ts
 */
public class JSONConvertUtil {

	/**
	 * objecct or List to json
	 * 
	 * @param object
	 * @return
	 */
	public static Object allObjectToJSON(Object object) {
		if (object instanceof List) {
			JSONArray arr = new JSONArray();
			List list = (List) object;
			for (Object obj : list) {
				JSONObject jsonObject = objectToJSON(obj);
				arr.add(jsonObject);
			}
			return arr;
		}
		JSONObject jsonObject = objectToJSON(object);
		return jsonObject;
	}

	/**
	 * object 转 json
	 * 
	 * @param object
	 * @return
	 */
	public static JSONObject objectToJSON(Object object) {
		JSONObject json = new JSONObject();
		if (object == null) {
			return json;
		}
		//如果object是Map
		if(object instanceof Map){
			json = JSONObject.fromObject(object);
			return json;
		}
		//如果object是系统中的一个实体类
		Method[] methodArr = object.getClass().getMethods();
		for (Method m : methodArr) {
			String mn = m.getName();
			if (!(mn.startsWith("get") && m.getParameterTypes().length == 0) || mn.equals("getClass")) continue;
			Class<?> classzz = m.getReturnType();
			if (classzz == Void.class) continue;
			Object value = getMethodValue(object, m);

			if (classzz == List.class) {
				String jsonKey = getNoteAnnValue(object, m);
				JSONArray array = toJSONArray(value);
				json.put(jsonKey, array);
			} else {
				String jsonKey = getNoteAnnValue(object, m);
				appendFieldValue(classzz, json, jsonKey, value);
			}

		}
		return json;
	}

	private static void appendFieldValue(Class classzz, JSONObject json, String jsonKey, Object value) {
		if (classzz == String.class || classzz == Integer.class || classzz == Long.class || classzz == Float.class || classzz == Double.class || classzz == int.class || classzz == long.class || classzz == double.class || classzz == float.class) {
			json.put(jsonKey, value);
		} else {// 对像
			JSONObject subObject = objectToJSON(value);
			if (subObject != null) {
				json.put(jsonKey, subObject);
			}
		}
	}

	private static String getNoteAnnValue(Object object, Method method) {
		JSONNoteName noteKeyAnn = method.getAnnotation(JSONNoteName.class);
		if (noteKeyAnn == null) {
			noteKeyAnn = (JSONNoteName) getFieldAnnotation(object, method, JSONNoteName.class);
		}
		String jsonKey = noteKeyAnn != null ? noteKeyAnn.value() : methodNameToFieldName(method.getName());
		return jsonKey;
	}

	/**
	 * 转成json array
	 * 
	 * @param value
	 * @return
	 */
	public static JSONArray toJSONArray(Object value) {
		if (value == null) return null;
		List list = (List) value;
		JSONArray array = new JSONArray();
		for (Object obj : list) {
			JSONObject subObject = objectToJSON(obj);
			if (subObject != null) {
				array.add(subObject);
			}
		}
		return array;
	}

	/**
	 * json转object
	 * 
	 * @param json
	 * @param obj
	 */
	public static void jsonToObject(JSONObject json, Object obj) {
		if (json == null) return;
		Iterator it = json.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = json.get(key);
			if (value == null) continue;
			Method method = getMethod(obj, key);
			if (method == null) continue;

			if (value instanceof JSONObject) {
				setJSONObjectType(obj, method, (JSONObject) value);
			} else if (value instanceof JSONArray) {
				setJSONArrayType(obj, method, (JSONArray) value);
			} else {// 基本类型
				setBaseType(obj, method, value);
			}
		}
	}

	/**
	 * json to map
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, Object> jsonToMap(JSONObject json) {
		Map<String, Object> root = new HashMap<String, Object>();
		Iterator it = json.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = json.get(key);
			if (value == null) continue;

			if (value instanceof JSONObject) {
				value = jsonToMap((JSONObject) value);
			} else if (value instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) value;
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(jsonArray.size());
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					Map<String, Object> map = jsonToMap(jsonObject);
					list.add(map);
				}
				value = list;
			}
			root.put(key, value);
		}
		return root;
	}

	/**
	 * 转json array
	 * 
	 * @param obj
	 * @param method
	 * @param jsonArray
	 */
	private static void setJSONArrayType(Object obj, Method method, JSONArray jsonArray) {
		ListObjectType arrayType = (ListObjectType) getFieldAnnotation(obj, method, ListObjectType.class);
		if (arrayType == null) return;
		List list = new ArrayList(jsonArray.size());
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Object subObject = newInstance(arrayType.value());
			jsonToObject(jsonObject, subObject);
			list.add(subObject);
		}
		invokeMethod(obj, method, list);
	}

	/**
	 * 设定object的值
	 * 
	 * @param obj
	 * @param method
	 * @param jsonObject
	 */
	private static void setJSONObjectType(Object obj, Method method, JSONObject jsonObject) {
		Class<?> _class = method.getParameterTypes()[0];
		Object subObject = newInstance(_class);

		jsonToObject(jsonObject, subObject);

		invokeMethod(jsonObject, method, subObject);

	}

	private static Object newInstance(Class<?> _class) {
		try {
			return _class.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private static void invokeMethod(Object obj, Method m, Object value) {
		if (value == null) return;
		try {
			m.invoke(obj, value);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private static Object getMethodValue(Object obj, Method m, Object... value) {
		try {
			return m.invoke(obj, value);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 设置基本类型的值
	 * 
	 * @param obj
	 * @param method
	 * @param value
	 */
	private static void setBaseType(Object obj, Method method, Object value) {
		if (value == null) return;
		Class<?> _class = method.getParameterTypes()[0];
		Object _value = null;
		try {
			if (_class == String.class) {
				_value = value.toString();
			} else if (_class == Integer.class || _class == int.class) {
				try {
					_value = Integer.parseInt(value.toString());
				} catch (Exception e) {
				}
			} else if (_class == Long.class || _class == long.class) {
				try {
					_value = Long.parseLong(value.toString());
				} catch (Exception e) {
				}
			} else if (_class == Float.class || _class == float.class) {
				try {
					_value = Float.parseFloat(value.toString());
				} catch (Exception e) {
				}
			} else if (_class == Double.class || _class == double.class) {
				try {
					_value = Double.parseDouble(value.toString());
				} catch (Exception e) {
				}
			} else {
				throw new RuntimeException("类型未指定");
			}

			invokeMethod(obj, method, _value);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	private static Method getMethod(Object obj, String name) {
		StringBuilder bd = new StringBuilder(name);
		String start = new String(new char[] { bd.charAt(0) });
		start = start.toUpperCase();
		bd.delete(0, 1);
		bd.insert(0, start);
		bd.insert(0, "set");

		String field = bd.toString();

		BeanInfo info = getBeanInfo(obj);
		if (info == null) return null;
		PropertyDescriptor[] pdArr = info.getPropertyDescriptors();
		for (int i = 0; i < pdArr.length; i++) {
			PropertyDescriptor pd = pdArr[i];
			Method method = pd.getWriteMethod();
			if (method == null) continue;
			String mName = method.getName();
			if (mName.equals(field) && method.getParameterTypes().length == 1) return method;
		}

		return null;

	}

	/**
	 * 返回一个字段的annotaction
	 * 
	 * @param obj
	 * @param method
	 * @param annotationClass
	 * @return
	 */
	private static Object getFieldAnnotation(Object obj, Method method, Class annotationClass) {
		String fieldName = methodNameToFieldName(method.getName());

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field f : fields) {
			if (f.getName().equals(fieldName)) {
				return f.getAnnotation(annotationClass);
			}
		}
		return null;
	}

	private static BeanInfo getBeanInfo(Object obj) {
		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(obj.getClass());
		} catch (IntrospectionException e) {
			return null;
		}
		return info;
	}

	/**
	 * 方法名转字段名
	 * 
	 * @param name
	 * @return
	 */
	private static String methodNameToFieldName(String name) {
		StringBuilder bd = new StringBuilder(name);
		String start = new String(new char[] { bd.charAt(3) });
		start = start.toLowerCase();
		bd.delete(0, 4);
		bd.insert(0, start);

		String field = bd.toString();

		return field;
	}
}
