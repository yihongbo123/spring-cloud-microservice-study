package com.tgw360.service;

import com.netflix.discovery.converters.Auto;
import com.tgw360.entity.Accounts;
import com.tgw360.entity.User;
import com.tgw360.mapper.AccountsMapper;
import com.tgw360.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AccountsMapper accountsMapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean save(User user){
        boolean b = userMapper.save(user);
//        int a = 10/0;
        Accounts accounts = new Accounts();
        accounts.setId(user.getId().intValue());
        accounts.setBalance(user.getId().intValue()*1000);
        accountsMapper.save(accounts);
        if (!b){
            return false;
        }
        return true;
    }

    public List<User> getAll() {
        return userMapper.findAll();
    }
}
