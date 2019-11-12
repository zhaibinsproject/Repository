package com.itmuch.cloud.study.repository;


import cn.springcloud.book.model.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<TUser, Long> {
}
