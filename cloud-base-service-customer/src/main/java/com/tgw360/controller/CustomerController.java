package com.tgw360.controller;

import com.tgw360.entity.Customer;
import com.tgw360.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/customer")
public class CustomerController {
    private final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;
    @Value("${aa.value}")
    private String num;
    @GetMapping("/{username}/{password}")
    public Customer findByUsernameAndAndPassword(@PathVariable("username") String username, @PathVariable("password") String password){
        Customer customer = null;
        try {
            customer = customerService.findByUsernameAndPassword(username, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  customer;
    }

    @GetMapping("/all")
    public List<Customer> findAll(){
        List<Customer> customers = null;
        try {
            customers = customerService.findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return customers;
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable Long id){
        logger.info("被调用！！");
        Customer customer = null;
        try {
            customer = customerService.findById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return customer;

    }

    @GetMapping("/num")
    public String getNum(){
        return num;
    }

}
