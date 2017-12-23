package com.itheima.view;

import java.util.Scanner;

import com.itheima.entity.User;
import com.itheima.service.UserService;

/**
 * 程序的入口
 * 
 * @author NewBoy
 *
 */
public class UserView {

	private static Scanner scanner = new Scanner(System.in);

	// 创建业务层
	private static UserService userService = new UserService();

	public static void main(String[] args) {
		while (true) {
			// 创建一个菜单
			System.out.println("请选择您的操作：");
			System.out.println("a. 登录 b. 注册 c. 退出");
			String op = scanner.next().toLowerCase();
			switch (op) {
			case "a":
				login(); // 登录
				break;

			default:
				break;
			}
		}

	}

	/**
	 * 登录
	 */
	private static void login() {
		System.out.println("--- 欢迎登录 --- ");
		System.out.println("请输入用户名：");
		String name = scanner.next();
		System.out.println("请输入密码：");
		String password = scanner.next();
		try {
			User user = userService.login(name, password);
			System.out.println("欢迎您，" + user.getName());
			// 进入后台的管理系统
			ProductView.goSystem();
		} catch (Exception e) {
			// 输出异常的信息
			System.out.println(e.getMessage());
		}
	}

}
