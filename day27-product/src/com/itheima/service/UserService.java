package com.itheima.service;

import com.itheima.dao.UserDao;
import com.itheima.entity.User;

/**
 * �û���ҵ���
 * @author NewBoy
 *
 */
public class UserService {
	
	private UserDao userDao = new UserDao();
	
	/**
	 * ��¼
	 */
	public User login(String name, String password) {
		//ͨ�����ֲ�ѯ�Ƿ�������û�
		User user = userDao.findByName(name);
		//�ж��û����Ƿ����
		if (user == null) {
			throw new RuntimeException("�û���������");
		}
		else {
			//�Ƚ������Ƿ����
			if (!user.getPassword().equals(password)) {
				//�����
				throw new RuntimeException("���벻��ȷ");
			}
			else {
				return user;
			}
		}
	}

}
