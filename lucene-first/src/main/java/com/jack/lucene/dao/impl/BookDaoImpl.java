package com.jack.lucene.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jack.lucene.dao.BookDao;
import com.jack.lucene.po.Book;

public class BookDaoImpl implements BookDao {
	public static void main(String[] args) {
		BookDaoImpl bookDao = new BookDaoImpl();
		List<Book> bookList = bookDao.queryBookList();
		for (Book book : bookList) {
			System.err.println(book);
		}
	}
	@Override
	public List<Book> queryBookList() {
		// TODO Auto-generated method stub
		// 图书集合list
		List<Book> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;

		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 创建数据库链接对象
			con = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/lucene", "root", "root");
			// 定义sql语句
			String sql = "select * from book";
			// 创建statement语句对象
			psmt = con.prepareStatement(sql);

			// 设置参数
			// 执行
			rs = psmt.executeQuery();
			// 处理结果集
			while (rs.next()) {
				// 创建图书对象
				Book book = new Book();

				// 图书id
				book.setId(rs.getInt("id"));
				// 图书名称
				book.setBookname(rs.getString("bookname"));
				// 图书价格
				book.setPrice(rs.getFloat("price"));
				// 图书图片
				book.setPic(rs.getString("pic"));
				// 图书描述
				book.setBookdesc(rs.getString("bookdesc"));

				list.add(book);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if (rs != null)
					rs.close();
				if (psmt != null)
					psmt.close();
				if (con != null)
					con.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return list;
	}

}
