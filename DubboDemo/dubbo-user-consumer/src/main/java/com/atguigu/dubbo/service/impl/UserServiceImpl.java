package com.atguigu.dubbo.service.impl;

import com.atguigu.dubbo.bean.Movie;
import com.atguigu.dubbo.bean.Order;
import com.atguigu.dubbo.bean.User;
import com.atguigu.dubbo.service.MovieService;
import com.atguigu.dubbo.service.UserService;

public class UserServiceImpl implements UserService {
    private MovieService movieService;

    public MovieService getMovieService() {
        return movieService;
    }

    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public Order buyNewMovie(User user) {
        Order order = new Order();

        Movie movie = movieService.getNewMovie();
        System.out.println("远程过程调用movie服务获取结果:"+movie);
        order.setMovieId(movie.getId());
        order.setMovieName(movie.getMovieName());
        order.setUserName(user.getUserName());
        return order;
    }

}
