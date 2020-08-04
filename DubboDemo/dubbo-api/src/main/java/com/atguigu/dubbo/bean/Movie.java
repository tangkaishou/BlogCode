package com.atguigu.dubbo.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
public class Movie implements Serializable {
    private Integer id;
    private String movieName;
    private Double price;
}
