package com.tgw360.controller;

import com.tgw360.entity.Customer;
import com.tgw360.entity.User;
import com.tgw360.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class FeignHystrixController {
    @Autowired
    private MessageService messageService;
    @RequestMapping("/{id}")
    public HashMap<String,Object> getUserAndCustomerByid(@PathVariable("id") Long id){
        return messageService.getUserAndCustomerByid(id);
    }
}
