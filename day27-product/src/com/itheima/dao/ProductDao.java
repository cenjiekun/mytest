package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.entity.Product;
import com.itheima.utils.DataSourceUtils;

/**
 * ��Ʒ���ݷ��ʲ�
 * 
 * @author NewBoy
 *
 */
public class ProductDao extends BaseDao {

	/**
	 * ��ѯ���еĲ�Ʒ
	 * 
	 * @return ���еĲ�Ʒ
	 */
	public List<Product> findAll() {
		try {
			return runner.query("select * from product", new BeanListHandler<Product>(Product.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * ͨ��id��ѯһ����¼
	 */
	public Product findById(int pid) {
		try {
			return runner.query("select * from product where pid=?", new BeanHandler<Product>(Product.class), pid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * �����Ʒ�����ݿ�
	 */
	public int save(Product product) {
		try {
			return runner.update("insert into product values(null, ?,?,?)", product.getPname(), product.getPrice(),
					product.getPdate());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * �޸���Ʒ
	 */
	public int update(Product product) {
		try {
			return runner.update("update product set pname=?,price=?,pdate=? where pid=?", product.getPname(),
					product.getPrice(), product.getPdate(), product.getPid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * ͨ��idɾ����Ʒ
	 */
	public int deleteById(int pid) {
		try {
			//updateҪʹ�������Ӷ���ķ���
			return runner.update(DataSourceUtils.getConnection(),  "delete from product where pid=?", pid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * ɾ�������Ʒ��һ������ǿ���ʵ�ֵģ�delete from product where pid in (1,2,3);
	 * ���ʹ�����񣬶��ɾ���Ĳ������빲��ͬһ�����Ӷ���
	 */

}
