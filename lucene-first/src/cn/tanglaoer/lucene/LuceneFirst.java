package cn.tanglaoer.lucene;

import jdk.internal.org.objectweb.asm.tree.analysis.Analyzer;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.*;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LuceneFirst {
    @Test
    public void createIndex() throws IOException {
        //1.创建一个Director对象，指定索引库保存的位置.
        FSDirectory directory = FSDirectory.open(new File("index").toPath());

        //2.基于Directory对象创建一个INdexWriter对象（索引库)
        IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig());
        //3.读取磁盘上的文件，对应每个文件创建一个文档对象
        File dir = new File("source");
        File[] files = dir.listFiles();
        for (File file : files) {
            String fileName = file.getName(); // 文件名
            String filePath = file.getPath(); // 文件的路径
            String fileContent = FileUtils.readFileToString(file, "utf-8");  // 文件的内容
            long fileSize = FileUtils.sizeOf(file);

            // 创建Field（域）
            //参数1：域的名称，参数2：域的内容，参数3：是否存储
            Field fieldName = new TextField("name", fileName, Field.Store.YES);
            //Field fieldPath = new TextField("path", filePath, Field.Store.YES);
            Field fieldPath = new StoredField("path", filePath);
            Field fieldContent = new TextField("content", fileContent, Field.Store.YES);
            //Field fieldSize = new TextField("size", fileSize + "", Field.Store.YES);
            Field fieldSizeValue = new LongPoint("size", fileSize);
            Field fieldSizeStore = new StoredField("size", fileSize);

            //创建文档对象
            Document document = new Document();

            //4.向文档对象中添加域
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
            //document.add(fieldSize);
            document.add(fieldSizeValue);
            document.add(fieldSizeStore);

            //5、把文档对象写入索引库
            indexWriter.addDocument(document);
        }
        //6.关闭indexwriter对象
        indexWriter.close();
    }

    @Test
    public void searchIndex() throws IOException {
        //1、创建一个Director对象，指定索引库的位置
        Directory directory = FSDirectory.open(new File("index").toPath());
        //2、创建一个IndexReader对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //3、创建一个IndexSearcher对象，构造方法中的参数indexReader对象。
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //4、创建一个Query对象，TermQuery
        Query query = new TermQuery(new Term("name", "apache"));
        //5、执行查询，得到一个TopDocs对象
        //参数1：查询对象 参数2：查询结果返回的最大记录数
        TopDocs topDocs = indexSearcher.search(query, 10);
        //6、取查询结果的总记录数
        System.out.println("查询总记录数：" + topDocs.totalHits);
        //7、取文档列表
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //8、打印文档中的内容
        for (ScoreDoc doc :
                scoreDocs) {
            //取文档id
            int docId = doc.doc;
            //根据id取文档对象
            Document document = indexSearcher.doc(docId);
            System.out.println("name" + ":" + document.get("name"));
            System.out.println("path" + ":" + document.get("path"));
            System.out.println("size" + ":" + document.get("size"));
            //System.out.println("content" + ":" + document.get("content"));
        }
        //9、关闭IndexReader对象
        indexReader.close();
    }

    @Test
    public void testTokenStream() throws Exception{
        //1) 创建一个Analyzer对象，StandardAnalyzer对象
        //StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
        IKAnalyzer ikAnalyzer = new IKAnalyzer(); // 切换分析器

        //2）使用分析器对象的tokenStream方法获得一个TokenStream对象
        TokenStream tokenStream = ikAnalyzer.tokenStream("", "全文检索是将整本书java、整篇文章中的任意内容信息查找出来的检索，java。它可以根据需要获得全文中有关章、节、段、句、词等信息，计算机程序通过扫描文章中的每一个词，对每一个词建立一个索引，指明该词在文章中出现的次数和位置，当用户查询时根据建立的索引查找，类似于通过字典的检索字表查字的过程。 .");

        //3）向TokenStream对象中设置一个引用，相当于是一个指针游标
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
        //4）调用TokenStream对象的reset方法，如果不调用抛异常
        tokenStream.reset();
        //5）使用while循环遍历TokenStream对象。
        while(tokenStream.incrementToken()){
            System.out.println(charTermAttribute.toString());
        }
        //6）关闭TokenStream对象。
        tokenStream.close();
    }

    @Test
    public void useIKAnalyzeCreateIndex() throws IOException {
        //1.创建一个Director对象，指定索引库保存的位置.
        FSDirectory directory = FSDirectory.open(new File("index").toPath());

        //2.基于Directory对象创建一个INdexWriter对象（索引库)
        //IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig());
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new IKAnalyzer());
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);

        //3.读取磁盘上的文件，对应每个文件创建一个文档对象
        File dir = new File("source");
        File[] files = dir.listFiles();
        for (File file : files) {
            String fileName = file.getName(); // 文件名
            String filePath = file.getPath(); // 文件的路径
            String fileContent = FileUtils.readFileToString(file, "utf-8");  // 文件的内容
            long fileSize = FileUtils.sizeOf(file);

            // 创建Field（域）
            //参数1：域的名称，参数2：域的内容，参数3：是否存储
            Field fieldName = new TextField("name", fileName, Field.Store.YES);
            //Field fieldPath = new TextField("path", filePath, Field.Store.YES);
            Field fieldPath = new StoredField("path", filePath);
            Field fieldContent = new TextField("content", fileContent, Field.Store.YES);
            //Field fieldSize = new TextField("size", fileSize + "", Field.Store.YES);
            Field fieldSizeValue = new LongPoint("size", fileSize);
            Field fieldSizeStore = new StoredField("size", fileSize);

            //创建文档对象
            Document document = new Document();

            //4.向文档对象中添加域
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
            //document.add(fieldSize);
            document.add(fieldSizeValue);
            document.add(fieldSizeStore);

            //5、把文档对象写入索引库
            indexWriter.addDocument(document);
        }
        //6.关闭indexwriter对象
        indexWriter.close();
    }
}
