package com.atguigu.dubbo;

import com.atguigu.dubbo.service.MovieService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ProviderMainApplication {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
        System.out.println("容器启动");
        MovieService movieService = context.getBean(MovieService.class);
        movieService.getNewMovie();

        context.start();
        System.in.read();
    }
}
