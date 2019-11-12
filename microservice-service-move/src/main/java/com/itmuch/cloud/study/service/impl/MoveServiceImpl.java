package com.itmuch.cloud.study.service.impl;

import cn.springcloud.book.feign.UserFeign;
import cn.springcloud.book.model.TUser;
import com.itmuch.cloud.study.service.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class MoveServiceImpl implements MoveService {

    @Autowired
    private UserFeign userFeign;

    @Override
    public TUser findUserById(Long userId) {
        TUser user = userFeign.findAllUsers().get(0);
        return user;
    }

    @Override
    public List<TUser> findAllUsers() {
        List<TUser> userList = userFeign.findAllUsers();
        return userList;
    }
}
