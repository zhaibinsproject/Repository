package com.itmuch.cloud.study.service;

import cn.springcloud.book.model.TUser;
import com.itmuch.cloud.study.Utils.SearchCondition;

import java.util.List;

public interface UserService {
    public TUser findUserById(TUser user);
    public List<TUser> findUsers(SearchCondition condition);
    public boolean updateUsers(List<TUser> userList);
    public TUser addUser(TUser user);
    public List<TUser> findAllUsers();
}
