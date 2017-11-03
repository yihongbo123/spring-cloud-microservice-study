package com.tgw360.controller;

import com.netflix.discovery.converters.Auto;
import com.tgw360.entity.Customer;
import com.tgw360.entity.User;
import com.tgw360.hystrixclient.CustomerFeignHystrixClient;
import com.tgw360.hystrixclient.UserFeignHystrixClient;
import com.tgw360.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
public class FeignHystrixController {
    @Autowired
    private MessageService messageService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @GetMapping("feign/{id}")
    public Customer findByIdFeign(@PathVariable("id") Long id) {
        return messageService.findByIdFeign(id);
    }
    @GetMapping("feign/aa/{id}")
    public HashMap<String,Object> findUserAndCustomerById(@PathVariable("id") Long id) {
        return messageService.findUserAndCustomerById(id,id);
    }
    @GetMapping("feign/all")
    public List<Customer> findAll() {
        return messageService.findAll();
    }

    @GetMapping("feign/user/{id}")
    public User findUserById(@PathVariable("id") Long id) {
        return messageService.findUserById(id);
    }
}
