package com.itmuch.cloud.study.controller;

import cn.springcloud.book.feign.UserFeign;
import cn.springcloud.book.model.CommonInfo;
import cn.springcloud.book.model.RequestMessage;
import cn.springcloud.book.model.TUser;
import com.alibaba.fastjson.JSON;
import com.itmuch.cloud.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {
  @Autowired
  private UserService userService;
  @Autowired
  private UserFeign userFeign;

  @RequestMapping("/getUser/{id}")
  public TUser getUserMess(@PathVariable Long id){
    TUser user = new TUser();
    user.setId(id);
    return this.userService.findUserById(user);
  }

  @RequestMapping("/getUsers")
  public List<TUser> getUserMess(){
    return this.userService.findAllUsers();
  }


  @RequestMapping("/addUser")
  public TUser saveUser(@RequestBody RequestMessage requestMessage){
    CommonInfo commonInfo = requestMessage.getCommonInfo();
    //转化成JSON字符串
    String userStr = JSON.toJSONString(requestMessage.getValue());
    //将JSON转化成对象
    TUser user = JSON.parseObject(userStr,TUser.class);
    user.setCreateTime(commonInfo.getRequestTime());
    return this.userService.addUser(user);
  }

  @RequestMapping(value = "/findAllUsers2",method = RequestMethod.POST)
  public List<TUser> findAllUsers(){
    List<TUser> users = userFeign.findAllUsers();
    return users;
  }
}
