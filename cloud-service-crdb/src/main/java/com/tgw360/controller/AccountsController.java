package com.tgw360.controller;

import com.tgw360.entity.Accounts;
import com.tgw360.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by 易弘博 on 2018/1/18 20:15
 */
@RestController
@RequestMapping("/accounts")
public class AccountsController {
    @Autowired
    private AccountsService accountsService;

    @GetMapping("/all")
    public List<Accounts> getAll(){
        return accountsService.getAll();
    }
    @GetMapping("/users")
    public List<Map<String, Object>> users(){
        return accountsService.users();
    }
}
