package com.jack.lucene.index;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.jack.lucene.dao.impl.BookDaoImpl;
import com.jack.lucene.po.Book;

public class IndexManager {

	private static final String INDEX_DIRECTORY = "F:\\develop\\index\\";

	// 建立索引
	@Test
	public void create() throws IOException {
		// 采集数据
		BookDaoImpl bookDao = new BookDaoImpl();
		List<Book> bookList = bookDao.queryBookList();

		// 建立文档对象
		List<Document> docList = new ArrayList<Document>();
		for (Book book : bookList) {
			// 建立文档对象
			Document doc = new Document();

			// 图书id
			// add方法：将域添加到文档中
			// 参数Field：域
			// TextField：文本域。参数name：域的名称;参数value：域值;
			// 参数store：指定是否将域值保存到文档中
			doc.add(new TextField("bookId", book.getId() + "", Store.YES));
			// 图书价格
			doc.add(new TextField("bookPrice", book.getPic() + "", Store.YES));
			// 图书图片
			doc.add(new TextField("bookPic", book.getPic() + "", Store.YES));
			// 图书描述
			doc.add(new TextField("bookDescr", book.getBookdesc() + "",
					Store.YES));
			// 图书名称
			doc.add(new TextField("bookName", book.getBookname() + "",
					Store.YES));

			docList.add(doc);
		}

		// 建立分析器对象(Analyzer)
		// Analyzer analyzer = new StandardAnalyzer();

		// 使用中文分词器
		IKAnalyzer analyzer = new IKAnalyzer();

		// 建立索引配置对象(Analyzer),用于分词
		// 参数一:指定使用的Lucene版本
		// 参数二:指定使用的分词器
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_10_3,
				analyzer);
		// 建立索引库目录对象
		File file = new File(INDEX_DIRECTORY);
		Directory directory = FSDirectory.open(file);
		// 创建索引写入对象(IndexWriterConfig),用户操作索引库
		// 参数一:索引位置对象
		// 参数二:配置对象
		IndexWriter writer = new IndexWriter(directory, iwc);

		// 使用indexwriter对象,将文档写入索引库
		for (Document document : docList) {
			writer.addDocument(document);
		}
		writer.close();
	}

	// 读取索引
	@Test
	public void ReadIndex() throws Exception {
		// 建立分析器对象(analyzer),用于分词
		// Analyzer analyzer = new StandardAnalyzer();

		// 使用中文分词器
		IKAnalyzer analyzer = new IKAnalyzer();

		// 建立查询对象(query)
		// bookname:Lucene
		// 参数一:指定默认搜索的对象
		// 参数二:分词对象
		QueryParser qp = new QueryParser("bookName", analyzer);

		// 使用查询解析器对象,解析实例化查询对象
		Query query = qp.parse("bookName:lucene");

		// 建立索引位置对象(Directory),指定索引的位置
		FSDirectory directory = FSDirectory.open(new File(INDEX_DIRECTORY));

		// 建立索引读取对象(IndexReader),用于将索引数据读取到内存中
		DirectoryReader reader = DirectoryReader.open(directory);

		// 建立索引搜索对象（IndexSearcher），执行搜索
		IndexSearcher searcher = new IndexSearcher(reader);

		// 使用IndexSearcher对象，执行搜索，返回搜索结果集（TopDocs）
		// 参数一：查询对象
		// 参数二n：搜索结果排序以后的前n个

		TopDocs topDocs = searcher.search(query, 10);

		// 处理结果集
		// 打印实际搜索到的结果数量

		System.out.println("搜索到的数量为" + topDocs.totalHits);

		// 处理结果集
		// 打印信息:文档id,文档的分值

		ScoreDoc[] scoreDocs = topDocs.scoreDocs;

		for (ScoreDoc sd : scoreDocs) {

			// 获取文档id和分值
			int docId = sd.doc;
			float score = sd.score;
			System.err.println("当前文档id:" + docId + ",当前文档分值" + score);

			// 根据文档id获取文档值
			// 相当于关系数据库中，根据主键id获取文档数据
			Document doc = searcher.doc(docId);

			System.out.println("图书id：" + doc.get("bookId"));
			System.out.println("图书名称：" + doc.get("bookName"));
			System.out.println("图书价格：" + doc.get("bookPrice"));
			System.out.println("图书图片：" + doc.get("bookPic"));
			System.out.println("图书描述：" + doc.get("bookDesc"));

		}
		// 释放资源
		reader.close();
	}

	// 读取索引(分页搜索)
	@Test
	public void ReadIndexPage() throws Exception {
		// 建立分析器对象(analyzer),用于分词
		// Analyzer analyzer = new StandardAnalyzer();

		// 使用中文分词器
		IKAnalyzer analyzer = new IKAnalyzer();

		// 建立查询对象(query)
		// bookname:Lucene
		// 参数一:指定默认搜索的对象
		// 参数二:分词对象
		QueryParser qp = new QueryParser("bookName", analyzer);

		// 使用查询解析器对象,解析实例化查询对象
		Query query = qp.parse("bookName:lucene");

		// 建立索引位置对象(Directory),指定索引的位置
		FSDirectory directory = FSDirectory.open(new File(INDEX_DIRECTORY));

		// 建立索引读取对象(IndexReader),用于将索引数据读取到内存中
		DirectoryReader reader = DirectoryReader.open(directory);

		// 建立索引搜索对象（IndexSearcher），执行搜索
		IndexSearcher searcher = new IndexSearcher(reader);

		// 使用IndexSearcher对象，执行搜索，返回搜索结果集（TopDocs）
		// 参数一：查询对象
		// 参数二n：搜索结果排序以后的前n个

		TopDocs topDocs = searcher.search(query, 10);

		// 处理结果集
		// 打印实际搜索到的结果数量

		System.out.println("搜索到的数量为" + topDocs.totalHits);

		// 处理结果集
		// 打印信息:文档id,文档的分值

		ScoreDoc[] scoreDocs = topDocs.scoreDocs;

		// 分页搜索=============================

		// 定义当前页
		int page = 2;

		// 每一页显示的大小
		int size = 2;

		// 计算当前页的记录开始数
		int star = (page - 1) * size;

		// 计算当前页的记录结束数
		// 需要取值star+size,与scoreDocs.length最小值
		int end = Math.max(star + size, scoreDocs.length);

		// 分页搜索=============================

		for (int i = star; i < end; i++) {

			// 获取文档id和分值
			int docId = scoreDocs[i].doc;
			float score = scoreDocs[i].score;
			System.err.println("当前文档id:" + docId + ",当前文档分值" + score);

			// 根据文档id获取文档值
			// 相当于关系数据库中，根据主键id获取文档数据
			Document doc = searcher.doc(docId);

			System.out.println("图书id：" + doc.get("bookId"));
			System.out.println("图书名称：" + doc.get("bookName"));
			System.out.println("图书价格：" + doc.get("bookPrice"));
			System.out.println("图书图片：" + doc.get("bookPic"));
			System.out.println("图书描述：" + doc.get("bookDesc"));

		}
		// 释放资源
		reader.close();
	}
}
