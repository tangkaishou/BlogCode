package cn.tanglaoer.springboot.controller;

import cn.tanglaoer.springboot.bean.TAdmin;
import cn.tanglaoer.springboot.service.TAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloController {

    @Autowired
    TAdminService adminService;

    @GetMapping("/hello")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "world") String name, Model model){
        System.out.println("hello world");
        model.addAttribute("name", name);
        return "hello";
    }

    @ResponseBody
    @GetMapping("/getTAdminById/{id}")
    public TAdmin getTAdminById(@PathVariable("id")Integer id){
        return adminService.getTAdminById(id);
    }

}
