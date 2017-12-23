package com.itheima.view;

import java.util.Scanner;

import com.itheima.entity.User;
import com.itheima.service.UserService;

/**
 * ��������
 * 
 * @author NewBoy
 *
 */
public class UserView {

	private static Scanner scanner = new Scanner(System.in);

	// ����ҵ���
	private static UserService userService = new UserService();

	public static void main(String[] args) {
		while (true) {
			// ����һ���˵�
			System.out.println("��ѡ�����Ĳ�����");
			System.out.println("a. ��¼ b. ע�� c. �˳�");
			String op = scanner.next().toLowerCase();
			switch (op) {
			case "a":
				login(); // ��¼
				break;

			default:
				break;
			}
		}

	}

	/**
	 * ��¼
	 */
	private static void login() {
		System.out.println("--- ��ӭ��¼ --- ");
		System.out.println("�������û�����");
		String name = scanner.next();
		System.out.println("���������룺");
		String password = scanner.next();
		try {
			User user = userService.login(name, password);
			System.out.println("��ӭ����" + user.getName());
			// �����̨�Ĺ���ϵͳ
			ProductView.goSystem();
		} catch (Exception e) {
			// ����쳣����Ϣ
			System.out.println(e.getMessage());
		}
	}

}
