package com.tgw360.service;

import com.tgw360.entity.Customer;

import java.util.List;

public interface CustomerService {
    public List<Customer> findAll();
    public Customer findByUsernameAndPassword(String username, String password);
    public Customer findById(Long id);
}
