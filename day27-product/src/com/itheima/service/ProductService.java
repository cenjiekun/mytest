package com.itheima.service;

import java.util.List;

import com.itheima.dao.ProductDao;
import com.itheima.entity.Product;
import com.itheima.utils.DataSourceUtils;

/**
 * ��Ʒ��ҵ���
 * @author NewBoy
 *
 */
public class ProductService {
	
	//���������ݷ��ʲ�
	private ProductDao productDao = new ProductDao();
	
	/**
	 * ��ѯ���е���Ʒ
	 * @return
	 */
	public List<Product> findAll() {
		return productDao.findAll();
	}
	
	/**
	 * ��ѯһ����Ʒ
	 */
	public Product findById(int pid) {
		return productDao.findById(pid);
	}
	
	/**
	 * ���һ����Ʒ
	 */
	public int addProduct(Product product) {
		return productDao.save(product);
	}
	
	/**
	 * �޸�һ����Ʒ
	 */
	public int modifyProduct(Product product) {
		return productDao.update(product);
	}
	
	/**
	 * ͨ��idɾ����Ʒ
	 */
	public int deleteProduct(int pid) {
		DataSourceUtils.startTransaction();
		int row = productDao.deleteById(pid);
		//�رղ��Ҵ�������ɾ��
		DataSourceUtils.commitAndClose();
		return row;
	}
	
	/**
	 * ɾ�������Ʒ
	 * (1,2,99);
	 * 
	 */
	public int deleteAll(int[] pids) {
		int count = 0;
		try {
			//��������
			DataSourceUtils.startTransaction();
			//��ε���ɾ���ķ���
			for (int i = 0; i < pids.length; i++) {
				count+=productDao.deleteById(pids[i]);
				//�쳣
				//System.out.println(100 / 0);
			}
			//�ύ����
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			//���쳣�ع�����
			DataSourceUtils.rollbackAndClose();
			throw new RuntimeException(e);
		}
		return count;
	}

}
