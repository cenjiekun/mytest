package com.itheima.entity;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Product {

	private int pid;
	private String pname;
	private double price;
	private Date pdate;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getPdate() {
		return pdate;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	@Override
	public String toString() {
		return "编号：" + pid + ", 名称：" + pname + ", 价格：" + new DecimalFormat("\u00A4#,###.00").format(price) + ", 生产日期："
				+ new SimpleDateFormat("yyyy年MM月dd日").format(pdate);
	}

	public Product(int pid, String pname, double price, Date pdate) {
		super();
		this.pid = pid;
		this.pname = pname;
		this.price = price;
		this.pdate = pdate;
	}

	public Product() {
		super();
	}

	public Product(String pname, double price, Date pdate) {
		super();
		this.pname = pname;
		this.price = price;
		this.pdate = pdate;
	}

}
