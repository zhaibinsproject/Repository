package com.itmuch.cloud.study.service.impl;

import cn.springcloud.book.model.TUser;
import com.itmuch.cloud.study.Utils.SearchCondition;
import com.itmuch.cloud.study.repository.UserRepository;
import com.itmuch.cloud.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public TUser findUserById(TUser user) {
        return this.userRepository.getOne(user.getId());
    }

    @Override
    public List<TUser> findUsers(SearchCondition condition) {
        return null;
    }

    @Override
    public boolean updateUsers(List<TUser> userList) {
        return false;
    }

    @Override
    public TUser addUser(TUser user) {
        TUser reUser = this.userRepository.save(user);
        return reUser;
    }

    @Override
    public List<TUser> findAllUsers() {
        return this.userRepository.findAll();
    }
}
