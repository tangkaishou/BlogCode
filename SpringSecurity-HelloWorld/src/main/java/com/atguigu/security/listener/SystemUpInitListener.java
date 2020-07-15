package com.atguigu.security.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SystemUpInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();
        String contextPath = application.getContextPath();
        //前端就可以使用 ${PATH}
        application.setAttribute("PATH", contextPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
