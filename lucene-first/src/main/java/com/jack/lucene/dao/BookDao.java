package com.jack.lucene.dao;

import java.util.List;

import com.jack.lucene.po.Book;

public interface BookDao {
	//查询全部书籍
	List<Book>queryBookList();
}
