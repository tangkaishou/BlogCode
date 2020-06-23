package cn.tanglaoer.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class SearchIndex {
    private IndexReader indexReader;
    private IndexSearcher indexSearcher;

    @Before
    public void init() throws IOException {
         indexReader = DirectoryReader.open(FSDirectory.open(new File("index").toPath()));
         indexSearcher = new IndexSearcher(indexReader);
    }

    /**
     * 范围查询
     * @throws IOException
     */
    @Test
    public void testRangeQuery() throws IOException {
        // 创建一个Query对象
        Query query = LongPoint.newRangeQuery("size", 0l, 10000l);
        printResult(query);
    }

    /**
     * 查询结果
     * @param query
     * @throws IOException
     */
    public void printResult(Query query) throws IOException {
        TopDocs topDocs = indexSearcher.search(query, 10);
        System.out.println("总记录数" + topDocs.totalHits);

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取文档id
            int docId = scoreDoc.doc;

            // 根据id去文档对象
            Document document = indexSearcher.doc(docId);
            System.out.println(document.get("name"));
            System.out.println(document.get("path"));
            System.out.println(document.get("size"));

            System.out.println("-----------");
        }
        indexReader.close();
    }


    /**
     * 使用QueryParser查询
     */
    @Test
    public void testQueryParser() throws ParseException, IOException {
        // 创建一个QueryParser对象，两个参数(百度查询)
        // 参数1：默认搜索域， 参数2：分析器对象
        QueryParser queryParser = new QueryParser("name", new IKAnalyzer());

        //使用QueryPaser对象创建一个Query对象
        Query query = queryParser.parse("lucene是一个java开发的全文检索工具包");
        // 打印查询结果
        printResult(query);

    }
}

