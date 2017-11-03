package com.tgw360.controller;

import com.tgw360.entity.Customer;
import com.tgw360.hystrixclient.UserFeignHystrixClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignHystrixController {
    @Autowired
    private UserFeignHystrixClient userFeignHystrixClient;

    @GetMapping("feign/{id}")
    public Customer findByIdFeign(@PathVariable Long id) {
        Customer customer = this.userFeignHystrixClient.findByIdFeign(id);
        return customer;

    }
}