package com.itmuch.cloud.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/getOne")
    public String test(){
        System.out.println("test1111111111111");
        return null;
    }

}
