package cn.tanglaoer.springboot.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class HelloListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("应用初始化-----》HelloListener");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("应用销毁了....HelloListener");
    }
}
