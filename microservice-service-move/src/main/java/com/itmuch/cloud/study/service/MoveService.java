package com.itmuch.cloud.study.service;

import cn.springcloud.book.model.TUser;

import java.util.List;


public interface MoveService {
    public TUser findUserById(Long userId);
    public List<TUser> findAllUsers();
}
