package com.tgw360.controller;

import com.tgw360.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tgw360.user.service.RibbonHystrixService;

@RestController
public class RibbonHystrixController {
    @Autowired
    private RibbonHystrixService ribbonHystrixService;

    @GetMapping("/ribbon/{id}")
    public Customer findById(@PathVariable Long id) {
        System.out.println("被调用！！");
        return this.ribbonHystrixService.findById(id);
    }
}