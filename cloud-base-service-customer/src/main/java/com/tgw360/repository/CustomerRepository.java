package com.tgw360.repository;


import com.tgw360.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    public List<Customer> findAll();

    public Customer findByUsernameAndPassword(String username,String password);
}
