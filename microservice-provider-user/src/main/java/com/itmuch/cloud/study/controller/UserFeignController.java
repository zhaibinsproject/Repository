package com.itmuch.cloud.study.controller;

import cn.springcloud.book.feign.UserFeign;
import cn.springcloud.book.model.TUser;
import com.itmuch.cloud.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserFeignController implements UserFeign {

    @Autowired
    private UserService userService;

    @Override
    @RequestMapping(value = "/findAllUsers",method = RequestMethod.POST)
    public List<TUser> findAllUsers() {
        return this.userService.findAllUsers();
    }
}
