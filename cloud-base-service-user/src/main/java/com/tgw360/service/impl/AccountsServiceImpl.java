package com.tgw360.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgw360.dao.master.AccountsDao;
import com.tgw360.domain.Accounts;
import com.tgw360.domain.User;
import com.tgw360.service.AccountsService;
import com.tgw360.service.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsServiceImpl implements AccountsService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private AccountsDao accountsDao;

    @Override
    public List<Accounts> getAll() {
        return accountsDao.findAll();
    }
}
