package com.atguigu.dubbo.service;

import com.atguigu.dubbo.bean.Order;
import com.atguigu.dubbo.bean.User;

public interface UserService {
    Order buyNewMovie(User user);
}
