package com.tgw360.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgw360.dao.master.UserDao;
import com.tgw360.domain.User;
import com.tgw360.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private UserDao userDao;
    @Override
    public User getUserByAccountId(Long id) {
            User customer = userDao.findByAccountId(id);
            return customer;
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }

}
