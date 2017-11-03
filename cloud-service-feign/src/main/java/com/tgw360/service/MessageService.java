package com.tgw360.service;

import com.tgw360.entity.Customer;
import com.tgw360.entity.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;

public interface MessageService {
    public Customer findByIdFeign(Long id);
    public HashMap<String,Object> findUserAndCustomerById(Long cid, Long uid);
    public List<Customer> findAll();
    public User findUserById(Long id);
}
