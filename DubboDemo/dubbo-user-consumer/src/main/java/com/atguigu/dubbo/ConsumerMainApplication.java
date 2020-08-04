package com.atguigu.dubbo;


import com.atguigu.dubbo.bean.Order;
import com.atguigu.dubbo.bean.User;
import com.atguigu.dubbo.service.UserService;
import com.atguigu.dubbo.service.impl.UserServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ConsumerMainApplication {
    public static void main(String[] args) throws IOException {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
            context.start();
            System.out.println("测试远程调用");
            UserService userService = context.getBean(UserService.class);
            System.out.println(userService);
            User user = new User();
            //测试这次远程调用是否成功
            Order order = userService.buyNewMovie(user);
            System.out.println(order);
            System.in.read();
        } catch (BeansException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
