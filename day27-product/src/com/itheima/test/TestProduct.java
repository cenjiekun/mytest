package com.itheima.test;

import java.sql.Date;

import com.itheima.entity.Product;

public class TestProduct {
	
	public static void main(String[] args) {
		Product product = new Product(1, "ÊÖ»ú", 20999.356, Date.valueOf("2000-11-11"));
		System.out.println(product);
	}

}
