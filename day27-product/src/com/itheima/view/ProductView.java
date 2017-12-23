package com.itheima.view;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.itheima.entity.Product;
import com.itheima.service.ProductService;

/**
 * 表示层
 * @author NewBoy
 *
 */
public class ProductView {
	
	//全局变量
	private static Scanner scanner = new Scanner(System.in);
	
	//调用业务类
	private static ProductService productService = new ProductService();
	
	/**
	 * 进入后台管理系统
	 */
	public static void goSystem() {
		System.out.println("-- 欢迎进入商品后台管理系统 --");
		while(true) {
			System.out.println("请选择操作菜单：");
			System.out.println("a.查询所有 b.通过id查询 c. 增加商品 d.修改商品 e.通过id删除 f. 删除多条商品 q.退出");
			//转成小写
			String op = scanner.next().toLowerCase();
			switch (op) {
			case "a":
				findAll();   //查询所有
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
				System.out.println("退出系统");
				System.exit(0);
				break;

			default:
				break;
			}
			
		}
		
	}

	/**
	 * 删除多条
	 */
	private static void deleteAll() {
		//显示所有商品
		findAll();
		System.out.println("请输入要删除的多条记录(1,2,3)：");
		String ids = scanner.next();
		//使用逗号分割成一个数组
		String [] arr = ids.split(",");
		//创建一个整数数组
		int[] pids = new int[arr.length];
		//遍历转换
		for (int i = 0; i < arr.length; i++) {
			pids[i] = Integer.parseInt(arr[i]);
		}
		//调用业务层
		try {
			int row = productService.deleteAll(pids);
			System.out.println("成功删除了" + row + "条记录");
		} catch (Exception e) {
			System.out.println("删除失败");
		}
	}

	/**
	 * 通过id删除商品
	 */
	private static void deleteById() {
		System.out.println("请输入要删除的商品编号：");
		int pid = scanner.nextInt();
		//查询是否有这件商品
		Product product = productService.findById(pid);
		if (product == null) {
			System.out.println("查无此商品，删除失败");
			return;
		}
		//有这件商品
		System.out.println("真的要删除此商品吗?(y/n)");
		System.out.println(product);
		String op = scanner.next();
		if ("y".equalsIgnoreCase(op)) {
			//调用业务逻辑方法删除
			if (productService.deleteProduct(pid) > 0 ) {
				System.out.println("删除成功");
			}
			else {
				System.out.println("删除失败");
			}
		}
		else {
			System.out.println("取消删除");
		}
	}

	/**
	 * 修改商品
	 */
	private static void modifyProduct() {
		System.out.println("请输入要修改的商品编号：");
		int pid = scanner.nextInt();
		Product product = productService.findById(pid);
		if (product == null) {
			System.out.println("查没此商品");
			return;
		}
		//已经查到了商品
		System.out.println("要修改的商品如下：");
		System.out.println(product);
		//修改属性
		System.out.println("请输入商品的名字：");
		String pname = scanner.next();
		System.out.println("请输入商品的价格");
		double price = scanner.nextDouble();
		String date = null;
		do {
			System.out.println("请输入商品生产日期(yyyy-MM-dd)：");
			date = scanner.next();
		}
		while(!date.matches("\\d{4}-\\d{2}-\\d{2}"));
		//日期的转换
		Date pdate = Date.valueOf(date);
		//对现有的产品进行属性修改
		product.setPname(pname);
		product.setPrice(price);
		product.setPdate(pdate);
		//调用业务层
		if (productService.modifyProduct(product) > 0) {
			System.out.println("修改成功");
		}
		else {
			System.out.println("修改失败");
		}
	}

	/**
	 * 添加商品
	 */
	private static void addProduct() {
		System.out.println("请输入商品名字：");
		String pname = scanner.next();
		System.out.println("请输入价格：");
		double price = scanner.nextDouble();
		String date = null;
		do {
			System.out.println("请输入日期(yyyy-MM-dd)：");
			date = scanner.next();
		}
		//对格式使用正则表达式进行验证
		while(!date.matches("\\d{4}-\\d{2}-\\d{2}"));
		//转成日期类型
		Date pdate = Date.valueOf(date);
		//封装数据
		Product product = new Product(pname, price, pdate);
		//调用业务层
		if (productService.addProduct(product)>0) {
			System.out.println("添加成功");
		}
		else {
			System.out.println("添加失败");
		}
	}

	/**
	 * 通过id查询一条记录
	 */
	private static void findById() {
		System.out.println("请输入要查询的商品的编号：");
		int pid = scanner.nextInt();
		Product product = productService.findById(pid);
		if (product == null) {
			System.out.println("查出无商品");
		}
		else {
			System.out.println("查询的商品如下：");
			System.out.println("----------------------------------------------------------------------------------------------------");
			System.out.println(product);
			System.out.println("----------------------------------------------------------------------------------------------------");
		}
	}

	/**
	 * 查询所有
	 */
	private static void findAll() {
		List<Product> products = productService.findAll();
		if (products == null || products.size() == 0) {
			System.out.println("没有商品");
		}
		else {
			System.out.println("所有的商品信息如下：");
			System.out.println("----------------------------------------------------------------------------------------------------");
			for (Product product : products) {
				System.out.println(product);
			}
			System.out.println("----------------------------------------------------------------------------------------------------");
		}
	}

}
