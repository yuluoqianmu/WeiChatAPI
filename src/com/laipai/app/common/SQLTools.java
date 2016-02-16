package com.laipai.app.common;

import java.util.ArrayList;
import java.util.List;

public class SQLTools {

	/**
	 * 把以 , 分开的id转成list
	 * 
	 * @param ids
	 * @return
	 */
	public static <T> List<T> stringToList(String ids, Class<T>... classzz) {
		List<T> params = new ArrayList<T>();
		if (ids == null) return params;
		String[] _ids = ids.split(",");
		for (String _id : _ids) {
			if (_id.length() < 1) continue;
			if (classzz == null || classzz.length == 0) {
				Integer id = Tools.stringToInteger(_id);
				if (id != null) {
					params.add((T) id);
				}
			} else if (classzz[0] == Long.class) {
				Long id = Tools.stringToLong(_id);
				if (id != null) {
					params.add((T) id);
				}
			} else if (classzz[0] == String.class) {
				params.add((T) _id);
			}
		}
		return params;
	}

	/**
	 * list转成字符串
	 * 
	 * @param list
	 * @return
	 */
	public static String listToString(List list) {
		StringBuffer bd = new StringBuffer();
		bd.append(",");
		for (Object o : list) {
			bd.append(o);
			bd.append(",");
		}
		return bd.toString();
	}

	/**
	 * 返回 in sql的 ? 字符串
	 * 
	 * @param params
	 * @return
	 */
	public static <T> String appendInSql(List<T> params) {
		String hql = "";
		for (int i = 0; i < params.size(); i++) {
			hql += "?";
			if (i < params.size() - 1) {
				hql += ",";
			}
		}
		return hql;
	}

}
