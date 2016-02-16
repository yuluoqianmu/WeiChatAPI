package com.lyz.db.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.log4j.Logger;

import com.lyz.db.IBaseDao;
import com.lyz.db.pool.MySQLDataBase;
import com.lyz.db.util.FieldsUtil;
import com.lyz.util.BaseTypeUtil;


public class BaseDaoImpl<T> implements IBaseDao<T> {

	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);

	private static final MySQLDataBase db = MySQLDataBase.getInstance();
	
	

	@Override
	public T queryObject(String sql, Class<T> clazz) {

		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		T obj = null;
		Collection<String> fields = getSqlFields(sql);
		logger.debug(sql);
		try {
			
			Map<String, Method> name2Method = new HashMap<String, Method>();
			Method[] methods = clazz.getMethods();
			/* 获取所有的set方法 */
			for (Method method : methods) {
				/* 不是set方法，则忽略 */
				String name = method.getName();
				if (!name.startsWith("set")) {
					continue;
				}
				name2Method.put(method.getName(), method);
			}

			conn = db.getConnection();
			stat = conn.prepareStatement(sql);
			result = stat.executeQuery();

			while (result.next()) {
				/* 实例化一个对象 */
				obj = clazz.newInstance();
				/* 给每一个指定的字段赋值 */
				for (String dbField : fields) {

					String setMethod = FieldsUtil.getJavaSetMethod(dbField);
					Method method = name2Method.get(setMethod);
					if(method == null){
						logger.error("fail to get setMethod for "+setMethod);
						continue;
					}
					Class<?>[] paramTypes = method.getParameterTypes();

					if (paramTypes[0] == int.class) {
						method.invoke(obj, result.getInt(dbField));
					} else if (paramTypes[0] == float.class) {
						method.invoke(obj, result.getFloat(dbField));
					} else if (paramTypes[0] == double.class) {
						method.invoke(obj, result.getDouble(dbField));
					} else if (paramTypes[0] == boolean.class) {
						method.invoke(obj, result.getBoolean(dbField));
					} else if (paramTypes[0] == long.class) {
						method.invoke(obj, result.getLong(dbField));
					} else if (paramTypes[0] == String.class) {
						method.invoke(obj, result.getString(dbField));
					} else if (paramTypes[0] == Date.class) {
						method.invoke(obj, result.getDate(dbField));
					} else if (paramTypes[0] == short.class) {
						method.invoke(obj, result.getShort(dbField));
					} else if (paramTypes[0] == byte.class) {
						method.invoke(obj, result.getByte(dbField));
					} else {
						logger.error("unknown method paramter type!"
								+ paramTypes[0]);
					}
				}
			}
		} catch (Exception e) {
			logger.error("fail to get queryObject!"+sql, e);
		} finally {
			close(result, stat, conn);
		}

		return obj;

	}

	@Override
	public List<T> queryObjects(String sql,
			Class<T> clazz) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		List<T> objList = new ArrayList<T>();
		Collection<String> fields = getSqlFields(sql);
		
		logger.debug(sql);
		try {
			
			Map<String, Method> name2Method = new HashMap<String, Method>();
			Method[] methods = clazz.getMethods();
			/* 获取所有的set方法 */
			for (Method method : methods) {
				/* 不是set方法，则忽略 */
				String name = method.getName();
				if (!name.startsWith("set")) {
					continue;
				}
				name2Method.put(method.getName(), method);
			}

			conn = db.getConnection();
			stat = conn.prepareStatement(sql);
			result = stat.executeQuery();

			while (result.next()) {
				/* 实例化一个对象 */
				T obj = clazz.newInstance();
				/* 给每一个指定的字段赋值 */
				for (String dbField : fields) {

					String setMethod = FieldsUtil.getJavaSetMethod(dbField);
					Method method = name2Method.get(setMethod);
					Class<?>[] paramTypes = method.getParameterTypes();

					if (paramTypes[0] == int.class) {
						method.invoke(obj, result.getInt(dbField));
					} else if (paramTypes[0] == float.class) {
						method.invoke(obj, result.getFloat(dbField));
					} else if (paramTypes[0] == double.class) {
						method.invoke(obj, result.getDouble(dbField));
					} else if (paramTypes[0] == boolean.class) {
						method.invoke(obj, result.getBoolean(dbField));
					} else if (paramTypes[0] == long.class) {
						method.invoke(obj, result.getLong(dbField));
					} else if (paramTypes[0] == String.class) {
						method.invoke(obj, result.getString(dbField));
					} else if (paramTypes[0] == Date.class) {
						method.invoke(obj, result.getDate(dbField));
					} else if (paramTypes[0] == short.class) {
						method.invoke(obj, result.getShort(dbField));
					} else if (paramTypes[0] == byte.class) {
						method.invoke(obj, result.getByte(dbField));
					} else {
						logger.error("unknown method paramter type!"
								+ paramTypes[0]);
					}				
				}//for fields
				
				objList.add(obj);
			}//for result
		} catch (Exception e) {
			logger.error("fail to get queryObject:"+sql, e);
		} finally {
			close(result, stat, conn);
		}

		return objList;
	}

	@Override
	public int upsertObject(String sql) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		int count = 0;
		logger.debug(sql);
		try {
			conn = db.getConnection();
			stat = conn.prepareStatement(sql);
			count = stat.executeUpdate();
		} catch (SQLException e) {
			logger.error("fail to upsertObject:"+sql,e);
			return count;
		} finally {
			close(result, stat, conn);
		}
		
		return count;
	}
	/**
	 * 返回插入语句
	 * 调用该方法，需要保证对象类中的属性顺序跟数据库中字段顺序一致
	 * 对象可以通过Table2JavaTool生成
	 * @param t
	 * @return
	 */
	public String getSqlForInsert(T t, String tableName){
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("insert into ").append(tableName)
		.append(" values(");
		
		try {
			int index = 0;
			Field[] fields = t.getClass().getDeclaredFields();
			for(Field field : fields){
				
				field.setAccessible(true);
				Object obj = field.get(t);
				
				if(field.getGenericType()==int.class 
						|| field.getGenericType()==short.class
						|| field.getGenericType()==int.class
						|| field.getGenericType()==long.class
						|| field.getGenericType()==float.class
						|| field.getGenericType()==double.class){
					if(obj == null){
						builder.append(0);
					}else{
						builder.append(obj);
					}
					
				}else{
					
					if(obj == null){
						builder.append("null");
					}else{
						builder.append("\'").append(obj).append("\'");
					}				
				}
				
				/*插入逗号*/
				if(index < fields.length-1){
					builder.append(", ");
				}
				index++;	
			}
			
			builder.append(")");
		} catch (Exception e) {
			logger.error("fail to getSql!",e);
			return null;
		}
		
		return builder.toString();
	}
	
	

	private void close(ResultSet result, PreparedStatement stat, Connection conn) {

		try {
			if (result != null) {
				result.close();
				result = null;
			}

			if (stat != null) {
				stat.close();
				stat = null;
			}

			if (conn != null) {
				db.releaseConnection(conn);
			}
		} catch (SQLException e) {
			logger.error("fail to release db conn!", e);
		}
	}
	/**
	 * 从sql语句中提取字段名称
	 * 限制：
	 * 1、不能使用*替代所有字段；
	 * 2、对于 count、avg、min、max的字段需要提供别名
	 * @param sql
	 * @return
	 */
	private List<String> getSqlFields(String sql){
		
		if(sql==null || "".equals(sql.trim())){
			return null;
		}
		
		List<String> fieldList = new ArrayList<String>();
		
		String fields = BaseTypeUtil.getSubString(sql, "select", "from").trim();
		
		String[] fieldArray = fields.split(",");
		
		for(String field : fieldArray){
			
			if("".equals(field.trim())){
				continue;
			}
			field = field.trim();
			/*限制查询语句中不能使用*替代所有*/
			if("*".equals(field)){
				logger.warn("* can not occur in select sql!");
				return null;
			}
			
			String[] fName = field.split(" ");
			if(fName.length == 1){/*没有别名的字段*/
				fieldList.add(fName[0]);
			}else{/*处理有别名的字段*/
				fieldList.add(fName[fName.length-1]);
			}
		}//fieldArray
		
		return fieldList;
		
	}

	@Override
	public int count(String sql) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		int count = 0;
		logger.debug(sql);
		try {
			conn = db.getConnection();
			stat = conn.prepareStatement(sql);
			result = stat.executeQuery();
			while (result.next()) {
				count = result.getInt(1);
			}
		} catch (SQLException e) {
			logger.error("fail to count:"+sql,e);
		} finally {
			close(result, stat, conn);
		}
		
		return count;
	}

	@Override
	public T queryObject(String sql, Class<T> clazz, Object... params) {
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		T obj = null;
		Collection<String> fields = getSqlFields(sql);
		logger.debug(sql);
		try {
			
			Map<String, Method> name2Method = new HashMap<String, Method>();
			Method[] methods = clazz.getMethods();
			/* 获取所有的set方法 */
			for (Method method : methods) {
				/* 不是set方法，则忽略 */
				String name = method.getName();
				if (!name.startsWith("set")) {
					continue;
				}
				name2Method.put(method.getName(), method);
			}

			conn = db.getConnection();
			stat = conn.prepareStatement(sql);
			if(params != null){
				for(int i=1; i<=params.length; i++){
					stat.setObject(i, params[i-1]);
				}
			}			
			result = stat.executeQuery();

			while (result.next()) {
				/* 实例化一个对象 */
				obj = clazz.newInstance();
				/* 给每一个指定的字段赋值 */
				for (String dbField : fields) {

					String setMethod = FieldsUtil.getJavaSetMethod(dbField);
					Method method = name2Method.get(setMethod);
					if(method == null){
						logger.error("no set method for "+setMethod);
						continue;
					}
					Class<?>[] paramTypes = method.getParameterTypes();

					if (paramTypes[0] == int.class) {
						method.invoke(obj, result.getInt(dbField));
					} else if (paramTypes[0] == float.class) {
						method.invoke(obj, result.getFloat(dbField));
					} else if (paramTypes[0] == double.class) {
						method.invoke(obj, result.getDouble(dbField));
					} else if (paramTypes[0] == boolean.class) {
						method.invoke(obj, result.getBoolean(dbField));
					} else if (paramTypes[0] == long.class) {
						method.invoke(obj, result.getLong(dbField));
					} else if (paramTypes[0] == String.class) {
						method.invoke(obj, result.getString(dbField));
					} else if (paramTypes[0] == Date.class) {
						method.invoke(obj, result.getDate(dbField));
					} else if (paramTypes[0] == short.class) {
						method.invoke(obj, result.getShort(dbField));
					} else if (paramTypes[0] == byte.class) {
						method.invoke(obj, result.getByte(dbField));
					} else {
						logger.error("unknown method paramter type!"
								+ paramTypes[0]);
					}
				}
			}
		} catch (Exception e) {
			logger.error("fail to get queryObject!"+sql, e);
		} finally {
			close(result, stat, conn);
		}

		return obj;
	}

	@Override
	public int upsertObject(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		int count = 0;
		logger.debug(sql);
		try {
			conn = db.getConnection();
			stat = conn.prepareStatement(sql);
			if(params != null){
				for(int i=1; i<=params.length; i++){
					stat.setObject(i, params[i-1]);
				}
			}	
			count = stat.executeUpdate();
		} catch (SQLException e) {
			logger.error("fail to upsertObject:"+sql,e);
		} finally {
			close(result, stat, conn);
		}
		
		return count;
	}

	@Override
	public List<T> queryObjects(String sql, Class<T> clazz, Object... params) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		List<T> objList = new ArrayList<T>();
		Collection<String> fields = getSqlFields(sql);
		
		logger.debug(sql);
		try {
			
			Map<String, Method> name2Method = new HashMap<String, Method>();
			Method[] methods = clazz.getMethods();
			/* 获取所有的set方法 */
			for (Method method : methods) {
				/* 不是set方法，则忽略 */
				String name = method.getName();
				if (!name.startsWith("set")) {
					continue;
				}
				name2Method.put(method.getName(), method);
			}

			conn = db.getConnection();
			stat = conn.prepareStatement(sql);
			if(params != null){
				for(int i=1; i<=params.length; i++){
					stat.setObject(i, params[i-1]);
				}
			}	
			result = stat.executeQuery();

			while (result.next()) {
				/* 实例化一个对象 */
				T obj = clazz.newInstance();
				/* 给每一个指定的字段赋值 */
				for (String dbField : fields) {

					String setMethod = FieldsUtil.getJavaSetMethod(dbField);
					Method method = name2Method.get(setMethod);
					Class<?>[] paramTypes = method.getParameterTypes();

					if (paramTypes[0] == int.class) {
						method.invoke(obj, result.getInt(dbField));
					} else if (paramTypes[0] == float.class) {
						method.invoke(obj, result.getFloat(dbField));
					} else if (paramTypes[0] == double.class) {
						method.invoke(obj, result.getDouble(dbField));
					} else if (paramTypes[0] == boolean.class) {
						method.invoke(obj, result.getBoolean(dbField));
					} else if (paramTypes[0] == long.class) {
						method.invoke(obj, result.getLong(dbField));
					} else if (paramTypes[0] == String.class) {
						method.invoke(obj, result.getString(dbField));
					} else if (paramTypes[0] == Date.class) {
						method.invoke(obj, result.getDate(dbField));
					} else if (paramTypes[0] == short.class) {
						method.invoke(obj, result.getShort(dbField));
					} else if (paramTypes[0] == byte.class) {
						method.invoke(obj, result.getByte(dbField));
					} else {
						logger.error("unknown method paramter type!"
								+ paramTypes[0]);
					}				
				}//for fields
				
				objList.add(obj);
			}//for result
		} catch (Exception e) {
			logger.error("fail to get queryObject:"+sql, e);
		} finally {
			close(result, stat, conn);
		}

		return objList;
	}

	@Override
	public int count(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		int count = 0;
		logger.debug(sql);
		try {
			conn = db.getConnection();
			stat = conn.prepareStatement(sql);
			if(params != null){
				for(int i=1; i<=params.length; i++){
					stat.setObject(i, params[i-1]);
				}
			}
			result = stat.executeQuery();
			while (result.next()) {
				count = result.getInt(1);
			}
		} catch (SQLException e) {
			logger.error("fail to count:"+sql,e);
		} finally {
			close(result, stat, conn);
		}
		
		return count;
	}

}
