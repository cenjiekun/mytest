package com.itheima.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.entity.User;

public class UserDao extends BaseDao {

	/**
	 * 通过名字进行查询
	 */
	public User findByName(String name) {
		try {
			return runner.query("select * from user where name=?", new BeanHandler<>(User.class), name);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
