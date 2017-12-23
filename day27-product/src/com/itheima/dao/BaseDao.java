package com.itheima.dao;

import org.apache.commons.dbutils.QueryRunner;

import com.itheima.utils.DataSourceUtils;

/**
 * 数据库访问类的基类
 * @author NewBoy
 *
 */
public class BaseDao {
	
	protected QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

}
