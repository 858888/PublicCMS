package com.sanluan.common.tools;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.sanluan.common.handler.PageHandler;

public class LuceneUtils {
	private static Analyzer analyzer = new SmartChineseAnalyzer();

	public static void main(String[] args) {
		try {
			Directory directory = getDirectory("E:\\a\\");
			IndexWriter indexWriter = getIndexWriter(directory);
			//indexWriter.deleteAll();
			Document doc = new Document();
			String text = "This is the text to be indexed.";
			doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
			indexWriter.addDocument(doc);
			indexWriter.close();
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static PageHandler serch(Directory directory, String fieldName, String word, int pageNo, int pageSize)
			throws IOException, URISyntaxException, ParseException {
		DirectoryReader directoryReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
		TopScoreDocCollector topScoreDocCollector = TopScoreDocCollector.create(pageNo * pageSize);
		Query query = new QueryParser(fieldName, analyzer).parse(word);
		indexSearcher.search(query, topScoreDocCollector);
		PageHandler pagination = new PageHandler(pageNo, pageSize, topScoreDocCollector.getTotalHits());
		ScoreDoc[] scoreDocs = topScoreDocCollector.topDocs(pagination.getFirstResult(), pageSize).scoreDocs;
		List<Document> list = new ArrayList<Document>();
		for (ScoreDoc scoreDoc : scoreDocs) {
			Document document = indexSearcher.doc(scoreDoc.doc);
			list.add(document);
		}
		pagination.setList(list);
		directoryReader.close();
		directory.close();
		return pagination;
	}

	public static IndexWriter getIndexWriter(Directory directory) throws IOException, URISyntaxException {
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		return new IndexWriter(directory, config);
	}

	public static Document getDocument() {
		return new Document();
	}

	public static Directory getDirectory(String dir) throws IOException, URISyntaxException {
		return FSDirectory.open(new File(dir).toPath());
	}
}
