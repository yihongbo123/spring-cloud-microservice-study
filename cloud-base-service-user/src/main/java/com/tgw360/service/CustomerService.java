package com.tgw360.service;

import com.tgw360.domain.User;

import java.util.List;

public interface CustomerService {
    public User getCustomerByAccountId(Long id);
    public List<User> getAll();
}
