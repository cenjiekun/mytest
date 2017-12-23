package com.itheima.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * ���ݿ⹤����
 * @author NewBoy
 *
 */
public class DataSourceUtils {
	
	private static ComboPooledDataSource ds = new ComboPooledDataSource();
	
	//ʹ���ֲ߳̾�����
	private static ThreadLocal<Connection> local = new ThreadLocal<>();
	
	/**
	 * �õ�����Դ
	 */
	public static DataSource getDataSource() {
		return ds;
	}
	
	/**
	 * �õ����Ӷ���
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException {
		//1. ���ֲ߳̾������еõ�һ������
		Connection conn = local.get();
		//2. �Ƿ�õ������û�У��򴴽�һ��
		if (conn == null) {
			conn = ds.getConnection();
			//�ŵ�������
			local.set(conn);
		}
		return conn;
	}
	
	/**
	 * ��������
	 */
	public static void startTransaction() {
		//�õ����Ӷ���
		try {
			Connection conn = getConnection();
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * �ύ���ر�
	 */
	public  static void commitAndClose() {
		try {
			//�õ����Ӷ���
			Connection conn = getConnection();
			if (conn != null) {
				conn.commit();
				//�ر�
				conn.close();
				//��������ɾ�����Ӷ���
				local.remove();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * �ع����ر�
	 */
	public static void rollbackAndClose() {
		try {
			//�õ����Ӷ���
			Connection conn = getConnection();
			if (conn != null) {
				conn.rollback();
				//�ر�
				conn.close();
				//��������ɾ�����Ӷ���
				local.remove();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
