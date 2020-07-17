package cn.tanglaoer.scw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SpringSessionController {
    @GetMapping("/set")
    public String setSession(HttpSession session){
        // session数据发生变化，一定要重新存储，否则，redis中数据就是旧的数据
        // 解决session一致性问题,将session持久化到redis中
        session.setAttribute("msg", "hello");
        return "ok";
    }

    @GetMapping("/get")
    public String getSession(HttpSession session){
        System.out.println(session.getClass());
        return (String) session.getAttribute("msg");
    }

}
