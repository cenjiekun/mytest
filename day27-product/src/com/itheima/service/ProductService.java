package com.itheima.service;

import java.util.List;

import com.itheima.dao.ProductDao;
import com.itheima.entity.Product;
import com.itheima.utils.DataSourceUtils;

/**
 * 商品的业务层
 * @author NewBoy
 *
 */
public class ProductService {
	
	//依赖于数据访问层
	private ProductDao productDao = new ProductDao();
	
	/**
	 * 查询所有的商品
	 * @return
	 */
	public List<Product> findAll() {
		return productDao.findAll();
	}
	
	/**
	 * 查询一件商品
	 */
	public Product findById(int pid) {
		return productDao.findById(pid);
	}
	
	/**
	 * 添加一件商品
	 */
	public int addProduct(Product product) {
		return productDao.save(product);
	}
	
	/**
	 * 修改一件商品
	 */
	public int modifyProduct(Product product) {
		return productDao.update(product);
	}
	
	/**
	 * 通过id删除商品
	 */
	public int deleteProduct(int pid) {
		DataSourceUtils.startTransaction();
		int row = productDao.deleteById(pid);
		//关闭并且从容器中删除
		DataSourceUtils.commitAndClose();
		return row;
	}
	
	/**
	 * 删除多件商品
	 * (1,2,99);
	 * 
	 */
	public int deleteAll(int[] pids) {
		int count = 0;
		try {
			//开启事务
			DataSourceUtils.startTransaction();
			//多次调用删除的方法
			for (int i = 0; i < pids.length; i++) {
				count+=productDao.deleteById(pids[i]);
				//异常
				//System.out.println(100 / 0);
			}
			//提交事务
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			//有异常回滚事务
			DataSourceUtils.rollbackAndClose();
			throw new RuntimeException(e);
		}
		return count;
	}

}
