package com.itheima.dao;

import org.apache.commons.dbutils.QueryRunner;

import com.itheima.utils.DataSourceUtils;

/**
 * ���ݿ������Ļ���
 * @author NewBoy
 *
 */
public class BaseDao {
	
	protected QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

}
