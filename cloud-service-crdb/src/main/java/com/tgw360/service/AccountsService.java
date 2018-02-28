package com.tgw360.service;

import com.tgw360.entity.Accounts;
import com.tgw360.mapper.AccountsMapper;
import com.tgw360.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AccountsService {
    @Autowired
    private AccountsMapper accountsMapper;

    @Transactional
    public List<Accounts> getAll(){
        return accountsMapper.findAll();
    }
    public List<Map<String,Object>> users(){
        return accountsMapper.finduser();
    }

    public boolean save(Accounts accounts){
        return accountsMapper.save(accounts);
    }
}
