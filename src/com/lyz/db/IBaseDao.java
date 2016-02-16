package com.lyz.db;

import java.util.List;

public interface IBaseDao<T> {
	/**
	 * 查询单个对象
	 * @param sql
	 * @param clazz T的类型，类属性字段需要和数据库字段一一对应（例如，数据库中为ming_cheng,java类中的对应字段为mingCheng）
	 * @return
	 */
	public T queryObject(String sql,Class<T> clazz);
	/**
	 * 查询单个对象
	 * @param sql
	 * @param clazz
	 * @param params 请求参数
	 * @return
	 */
	public T queryObject(String sql,Class<T> clazz, Object... params);
	/**
	 * 插入、更新或者删除数据
	 * @param sql
	 * @return 影响的行数
	 */
	public int upsertObject(String sql);
	
	public int upsertObject(String sql, Object... params);
	
	/**
	 * 查询多个数据
	 * @param sql
	 * @param clazz T的类型
	 * @return
	 */
	public List<T> queryObjects(String sql,Class<T> clazz);
	
	public List<T> queryObjects(String sql,Class<T> clazz, Object... params);
	/**
	 * 计数
	 * @param sql
	 * @return
	 */
	public int count(String sql);
	
	public int count(String sql, Object... params);
	/**
	 * 返回插入语句
	 * 调用该方法，需要保证对象类中的属性顺序跟数据库中字段顺序一致
	 * 对象可以通过Table2JavaTool生成
	 * @param t
	 * @return
	 */
	public String getSqlForInsert(T t, String tableName);

}
