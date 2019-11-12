package com.itmuch.cloud.study.controller;

import cn.springcloud.book.model.TUser;
import com.itmuch.cloud.study.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MoveController {

  @Autowired
  private MoveService userService;

  @RequestMapping("/getUser/{id}")
  public TUser getUserMess(@PathVariable Long id){
    return this.userService.findUserById(id);
  }

  @RequestMapping(value = "/getAllUsers",method = RequestMethod.POST)
  public List<TUser> getAllUsers(){
    return this.userService.findAllUsers();
  }
}
