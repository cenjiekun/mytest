package com.itheima.view;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.itheima.entity.Product;
import com.itheima.service.ProductService;

/**
 * ��ʾ��
 * @author NewBoy
 *
 */
public class ProductView {
	
	//ȫ�ֱ���
	private static Scanner scanner = new Scanner(System.in);
	
	//����ҵ����
	private static ProductService productService = new ProductService();
	
	/**
	 * �����̨����ϵͳ
	 */
	public static void goSystem() {
		System.out.println("-- ��ӭ������Ʒ��̨����ϵͳ --");
		while(true) {
			System.out.println("��ѡ������˵���");
			System.out.println("a.��ѯ���� b.ͨ��id��ѯ c. ������Ʒ d.�޸���Ʒ e.ͨ��idɾ�� f. ɾ��������Ʒ q.�˳�");
			//ת��Сд
			String op = scanner.next().toLowerCase();
			switch (op) {
			case "a":
				findAll();   //��ѯ����
				break;
			case "b":
				findById();
				break;
			case "c":
				addProduct();
				break;
			case "d":
				modifyProduct();
				break;
			case "e":
				deleteById();
				break;
			case "f":
				deleteAll();
				break;
			case "q":
				System.out.println("�˳�ϵͳ");
				System.exit(0);
				break;

			default:
				break;
			}
			
		}
		
	}

	/**
	 * ɾ������
	 */
	private static void deleteAll() {
		//��ʾ������Ʒ
		findAll();
		System.out.println("������Ҫɾ���Ķ�����¼(1,2,3)��");
		String ids = scanner.next();
		//ʹ�ö��ŷָ��һ������
		String [] arr = ids.split(",");
		//����һ����������
		int[] pids = new int[arr.length];
		//����ת��
		for (int i = 0; i < arr.length; i++) {
			pids[i] = Integer.parseInt(arr[i]);
		}
		//����ҵ���
		try {
			int row = productService.deleteAll(pids);
			System.out.println("�ɹ�ɾ����" + row + "����¼");
		} catch (Exception e) {
			System.out.println("ɾ��ʧ��");
		}
	}

	/**
	 * ͨ��idɾ����Ʒ
	 */
	private static void deleteById() {
		System.out.println("������Ҫɾ������Ʒ��ţ�");
		int pid = scanner.nextInt();
		//��ѯ�Ƿ��������Ʒ
		Product product = productService.findById(pid);
		if (product == null) {
			System.out.println("���޴���Ʒ��ɾ��ʧ��");
			return;
		}
		//�������Ʒ
		System.out.println("���Ҫɾ������Ʒ��?(y/n)");
		System.out.println(product);
		String op = scanner.next();
		if ("y".equalsIgnoreCase(op)) {
			//����ҵ���߼�����ɾ��
			if (productService.deleteProduct(pid) > 0 ) {
				System.out.println("ɾ���ɹ�");
			}
			else {
				System.out.println("ɾ��ʧ��");
			}
		}
		else {
			System.out.println("ȡ��ɾ��");
		}
	}

	/**
	 * �޸���Ʒ
	 */
	private static void modifyProduct() {
		System.out.println("������Ҫ�޸ĵ���Ʒ��ţ�");
		int pid = scanner.nextInt();
		Product product = productService.findById(pid);
		if (product == null) {
			System.out.println("��û����Ʒ");
			return;
		}
		//�Ѿ��鵽����Ʒ
		System.out.println("Ҫ�޸ĵ���Ʒ���£�");
		System.out.println(product);
		//�޸�����
		System.out.println("��������Ʒ�����֣�");
		String pname = scanner.next();
		System.out.println("��������Ʒ�ļ۸�");
		double price = scanner.nextDouble();
		String date = null;
		do {
			System.out.println("��������Ʒ��������(yyyy-MM-dd)��");
			date = scanner.next();
		}
		while(!date.matches("\\d{4}-\\d{2}-\\d{2}"));
		//���ڵ�ת��
		Date pdate = Date.valueOf(date);
		//�����еĲ�Ʒ���������޸�
		product.setPname(pname);
		product.setPrice(price);
		product.setPdate(pdate);
		//����ҵ���
		if (productService.modifyProduct(product) > 0) {
			System.out.println("�޸ĳɹ�");
		}
		else {
			System.out.println("�޸�ʧ��");
		}
	}

	/**
	 * �����Ʒ
	 */
	private static void addProduct() {
		System.out.println("��������Ʒ���֣�");
		String pname = scanner.next();
		System.out.println("������۸�");
		double price = scanner.nextDouble();
		String date = null;
		do {
			System.out.println("����������(yyyy-MM-dd)��");
			date = scanner.next();
		}
		//�Ը�ʽʹ��������ʽ������֤
		while(!date.matches("\\d{4}-\\d{2}-\\d{2}"));
		//ת����������
		Date pdate = Date.valueOf(date);
		//��װ����
		Product product = new Product(pname, price, pdate);
		//����ҵ���
		if (productService.addProduct(product)>0) {
			System.out.println("��ӳɹ�");
		}
		else {
			System.out.println("���ʧ��");
		}
	}

	/**
	 * ͨ��id��ѯһ����¼
	 */
	private static void findById() {
		System.out.println("������Ҫ��ѯ����Ʒ�ı�ţ�");
		int pid = scanner.nextInt();
		Product product = productService.findById(pid);
		if (product == null) {
			System.out.println("�������Ʒ");
		}
		else {
			System.out.println("��ѯ����Ʒ���£�");
			System.out.println("----------------------------------------------------------------------------------------------------");
			System.out.println(product);
			System.out.println("----------------------------------------------------------------------------------------------------");
		}
	}

	/**
	 * ��ѯ����
	 */
	private static void findAll() {
		List<Product> products = productService.findAll();
		if (products == null || products.size() == 0) {
			System.out.println("û����Ʒ");
		}
		else {
			System.out.println("���е���Ʒ��Ϣ���£�");
			System.out.println("----------------------------------------------------------------------------------------------------");
			for (Product product : products) {
				System.out.println(product);
			}
			System.out.println("----------------------------------------------------------------------------------------------------");
		}
	}

}
