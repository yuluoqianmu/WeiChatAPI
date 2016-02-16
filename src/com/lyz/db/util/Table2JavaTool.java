package com.lyz.db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.lyz.db.pool.MySQLDataBase;


/**
 * 把数据表转换成java类的工具类
 * @author luyongzhao
 *
 */
public class Table2JavaTool {
	
	private static final Logger logger = Logger.getLogger(Table2JavaTool.class);
	
	private static final MySQLDataBase db = MySQLDataBase.getInstance();
	/**
	 * 获取从数据库反射生成的java类
	 * @param tableName
	 * @return
	 */
	public static String getJavaClassStr(String tableName){
		
		Connection conn = null;
		PreparedStatement stat = null;
		ResultSet result = null;
		
		String sql = "SHOW FULL COLUMNS FROM "+tableName;
		String className = FieldsUtil.getJavaClassName(tableName);
		StringBuilder builder = new StringBuilder("public class ");
		builder.append(className).append("{\n\n");
		
		try {
			conn = db.getConnection();
			stat = conn.prepareStatement(sql);
			result = stat.executeQuery();
			while(result.next()){
				String fieldName = result.getString("Field");
				String type = result.getString("Type");
				String comment = result.getString("Comment");
				/*添加注释*/
				builder.append("\t/*").append(comment).append("*/\n");
				/*插入属性*/
				builder.append("\t").append("private ").append(FieldsUtil.getJavaTypeFromSqlType(type))
				.append(" ").append(FieldsUtil.getJavaClassAttr(fieldName)).append(";\n\n");
			}
			builder.append("}");
		} catch (SQLException e) {
			logger.error("fail to generate java code!",e);
		} finally{
			close(result, stat, conn);
		}
		
		return builder.toString();
	}
	
	
	
	private static void close(ResultSet result, PreparedStatement stat, Connection conn) {

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
	
	public static void main(String args[]){
		
//		System.out.println(Table2JavaTool.getJavaClassStr("zu_ke"));
		System.out.println(Table2JavaTool.getJavaClassStr("v_lp_galary_app_show"));
	}
}
