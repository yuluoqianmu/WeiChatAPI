package com.lyz.db.pool;

import java.sql.Connection;


import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.jolbox.bonecp.BoneCPConfig;


public class MySQLDataBase{
	private final static Log logger = LogFactory.getLog(MySQLDataBase.class);

	private static BoneCPWrapper connectionPool;
	
	public final String CONN_CONFIG_NAME = "dbName";
	
	private static AtomicReference<MySQLDataBase> instance = new AtomicReference<MySQLDataBase>();
	
	
	/**
	 * 构造函数
	 * 
	 * @param config
	 *            配置文件名称
	 * @throws ServiceException
	 */
	private MySQLDataBase(){
		doInit();
	}
	
	public static MySQLDataBase getInstance(){
		if(instance.get() == null){
			instance.compareAndSet(null, new MySQLDataBase());
		}

		
		return instance.get();
	}

	/**
	 * 初始化数据库
	 */
	public void doInit(){
		createConnectionPool(CONN_CONFIG_NAME);
	}
	
	private void createConnectionPool(String connConfigName){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 设置配置参数
			// 默认会去找bonecp-config.xml文件
			BoneCPConfig boneConf = new com.jolbox.bonecp.BoneCPConfig(
					connConfigName);
			// 连接释放处理
//			boneConf.setReleaseHelperThreads(10);
//			boneConf.setStatementReleaseHelperThreads(10);
			// 当连接池中的连接耗尽的时候 BoneCP一次同时获取的连接数
			boneConf.setAcquireIncrement(10);
//			// 设置连接空闲时间
//			boneConf.setIdleMaxAge(10,TimeUnit.SECONDS);
			// 设置连接空闲时间
			boneConf.setIdleMaxAge(60,TimeUnit.MINUTES);
			// 每 秒检查所有连接池中的空闲连接
			boneConf.setIdleConnectionTestPeriod(1,TimeUnit.MINUTES);
//			idleConnectionTestPeriod:检查数据库连接池中控线连接的间隔时间，单位是分，默认值：240，如果要取消则设置为0 
//			idleMaxAge:连接池中未使用的链接最大存活时间，单位是分，默认值：60，如果要永远存活设置为0 
			// 创建录入库连接池
			connectionPool = new BoneCPWrapper(boneConf); // setup the connection pool		
			// 获取连接
//			connection = connectionPool.getConnection(); // fetch a connection
		} catch (ClassNotFoundException e) {
			logger.warn("找不到类异常.", e);
		} catch (SQLException e) {
			logger.warn("数据库异常:", e);
		} catch (Exception e) {
			logger.warn("异常:", e);
		} 
	}

	/**
	 * 停止数据库
	 */
	public void doStop(){
		connectionPool.shutdown();
	}
	
	public Connection getConnection(){
		try {	
			return connectionPool.getConnection();
		} catch (SQLException e) {
			logger.error("getConnection 发生异常", e);
			return null;
		}
	}
	
	/**
	 * 释放连接回连接池，默认是非事件的连接
	 * @param conn
	 */
	public void releaseConnection(Connection conn){
		
		if(conn == null){
			return;
		}
		
		connectionPool.releaseConnection(conn);
	
	}

}
