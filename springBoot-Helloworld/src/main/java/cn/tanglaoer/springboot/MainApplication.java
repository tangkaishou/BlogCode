package cn.tanglaoer.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@ServletComponentScan //扫描@WebServlet  @WebFilter   @WebListener
//@MapperScan("cn.tanglaoer.springboot.mapper") //扫描mapper,不用这个，直接在mapper使用@Mapper也可以
@EnableTransactionManagement //启用声明式事务
@SpringBootApplication // 声明当前项目为SpringBoot项目，写的controller要在springboot的包下
public class MainApplication {
    public static void main(String[] args) {
        // 启动内置Tomcat
        SpringApplication.run(MainApplication.class, args);
    }
}
