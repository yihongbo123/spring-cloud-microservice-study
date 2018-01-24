package com.tgw360.service;

import com.tgw360.domain.User;

import java.util.List;

public interface UserService {
    public User getUserByAccountId(Long id);
    public List<User> getAll();
}
