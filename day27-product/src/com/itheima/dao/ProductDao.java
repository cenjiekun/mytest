package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.entity.Product;
import com.itheima.utils.DataSourceUtils;

/**
 * 产品数据访问层
 * 
 * @author NewBoy
 *
 */
public class ProductDao extends BaseDao {

	/**
	 * 查询所有的产品
	 * 
	 * @return 所有的产品
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
	 * 通过id查询一条记录
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
	 * 添加商品到数据库
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
	 * 修改商品
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
	 * 通过id删除商品
	 */
	public int deleteById(int pid) {
		try {
			//update要使用有连接对象的方法
			return runner.update(DataSourceUtils.getConnection(),  "delete from product where pid=?", pid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除多件商品，一条语句是可以实现的：delete from product where pid in (1,2,3);
	 * 如果使用事务，多个删除的操作必须共享同一个连接对象
	 */

}
