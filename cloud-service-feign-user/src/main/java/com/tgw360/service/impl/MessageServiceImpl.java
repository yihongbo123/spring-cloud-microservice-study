package com.tgw360.service.impl;

import com.tgw360.entity.Customer;
import com.tgw360.entity.User;
import com.tgw360.hystrixclient.FeignHystrixClient;
import com.tgw360.hystrixclient.UserFeignHystrixClient;
import com.tgw360.repository.UserRepository;
import com.tgw360.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private FeignHystrixClient feignHystrixClient;
    @Override
    public HashMap<String, Object> getUserAndCustomerByid(Long id) {
        return feignHystrixClient.findCustomerById(id);
    }
}
