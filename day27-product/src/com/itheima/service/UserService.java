package com.itheima.service;

import com.itheima.dao.UserDao;
import com.itheima.entity.User;

/**
 * 用户的业务层
 * @author NewBoy
 *
 */
public class UserService {
	
	private UserDao userDao = new UserDao();
	
	/**
	 * 登录
	 */
	public User login(String name, String password) {
		//通过名字查询是否有这个用户
		User user = userDao.findByName(name);
		//判断用户名是否存在
		if (user == null) {
			throw new RuntimeException("用户名不存在");
		}
		else {
			//比较密码是否相等
			if (!user.getPassword().equals(password)) {
				//不相等
				throw new RuntimeException("密码不正确");
			}
			else {
				return user;
			}
		}
	}

}
