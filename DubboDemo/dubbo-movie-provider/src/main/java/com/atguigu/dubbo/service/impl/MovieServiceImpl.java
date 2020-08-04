package com.atguigu.dubbo.service.impl;

import com.atguigu.dubbo.bean.Movie;
import com.atguigu.dubbo.service.MovieService;

public class MovieServiceImpl implements MovieService {
    @Override
    public Movie getNewMovie() {
        Movie movie = new Movie();
        movie.setId(1);
        movie.setMovieName("妇联4");
        movie.setPrice(78.4);
        System.out.println(movie);
        return movie;
    }
}
