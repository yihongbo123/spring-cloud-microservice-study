package com.tgw360.service.impl;

import com.tgw360.dao.master.AccountsDao;
import com.tgw360.domain.Accounts;
import com.tgw360.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsServiceImpl implements AccountsService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private AccountsDao accountsDao;

    @Override
    public List<Accounts> getAll() {
        return accountsDao.findAll();
    }
}
