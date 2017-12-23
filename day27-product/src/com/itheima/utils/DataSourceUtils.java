package com.itheima.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据库工具类
 * @author NewBoy
 *
 */
public class DataSourceUtils {
	
	private static ComboPooledDataSource ds = new ComboPooledDataSource();
	
	//使用线程局部变量
	private static ThreadLocal<Connection> local = new ThreadLocal<>();
	
	/**
	 * 得到数据源
	 */
	public static DataSource getDataSource() {
		return ds;
	}
	
	/**
	 * 得到连接对象
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		//1. 从线程局部容器中得到一个连接
		Connection conn = local.get();
		//2. 是否得到，如果没有，则创建一个
		if (conn == null) {
			conn = ds.getConnection();
			//放到容器中
			local.set(conn);
		}
		return conn;
	}
	
	/**
	 * 开启事务
	 */
	public static void startTransaction() {
		//得到连接对象
		try {
			Connection conn = getConnection();
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 提交并关闭
	 */
	public  static void commitAndClose() {
		try {
			//得到连接对象
			Connection conn = getConnection();
			if (conn != null) {
				conn.commit();
				//关闭
				conn.close();
				//从容器中删除连接对象
				local.remove();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 回滚并关闭
	 */
	public static void rollbackAndClose() {
		try {
			//得到连接对象
			Connection conn = getConnection();
			if (conn != null) {
				conn.rollback();
				//关闭
				conn.close();
				//从容器中删除连接对象
				local.remove();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
