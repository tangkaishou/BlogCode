package cn.tanglaoer.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;

public class IndexManager {
    private IndexWriter indexWriter;
    @Before
    public void init() throws IOException {
        indexWriter =
                new IndexWriter(FSDirectory.open(new File("index").toPath()),
                        new IndexWriterConfig(new IKAnalyzer()));
    }

    /**
     * 添加文档
     * @throws IOException
     */
    @Test
    public void addDocument() throws IOException {
        //创建一个IndexWriter对象，需要使用IKAnalyzer作为分析器

        //创建一个Document对象
        Document document = new Document();
        //向document对象中添加域
        document.add(new TextField("name", "新添加的文件", Field.Store.YES));
        document.add(new TextField("content", "新添加的文件内容", Field.Store.NO));
        document.add(new StoredField("path", "helo"));
        // 把文档写入索引库
        indexWriter.addDocument(document);
        //关闭索引库
        indexWriter.close();
    }

    /**
     * 删除全部文档
      */
    @Test
    public void deleteDocument() throws IOException {
        // 删除全部文档
        indexWriter.deleteAll();
        // 关闭索引库
        indexWriter.close();
    }

    /**
     * 根据Query删除文档
     */
    @Test
    public void deleteDocumentByQuery() throws IOException {
       // 根据查询删除
       indexWriter.deleteDocuments(new Term("name", "apache"));
       indexWriter.close();
    }

    @Test
    public void updateDocument() throws IOException {
        // 创建一个新的文档对象
        Document document = new Document();
        // 向文档对象中添加域
        document.add(new TextField("name", "更新之后的文档", Field.Store.YES));
        document.add(new TextField("name1", "更新之后的文档2", Field.Store.YES));
        document.add(new TextField("name2", "更新之后的文档3", Field.Store.YES));

        // 更新操作
        indexWriter.updateDocument(new Term("name", "spring"), document);
        indexWriter.close();

    }
}
