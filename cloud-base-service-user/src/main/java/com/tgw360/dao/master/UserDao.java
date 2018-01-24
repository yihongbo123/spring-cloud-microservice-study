package com.tgw360.dao.master;

import com.tgw360.domain.User;

import java.util.List;

//@Mapper
public interface UserDao {

    User findByAccountId(Long id);
    List<User> findAll();
}
