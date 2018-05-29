package com.tgw360.service.impl;

import com.netflix.discovery.converters.Auto;
import com.tgw360.entity.Customer;
import com.tgw360.entity.User;
import com.tgw360.hystrixclient.CustomerFeignHystrixClient;
import com.tgw360.hystrixclient.UserFeignHystrixClient;
import com.tgw360.repository.UserRepository;
import com.tgw360.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private UserFeignHystrixClient userFeignHystrixClient;
    @Autowired
    private CustomerFeignHystrixClient customerFeignHystrixClient;
    @Autowired
    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Customer findByIdFeign(Long id) {
        try {
            Customer customer = this.customerFeignHystrixClient.findCustomerById(id);
            System.out.println("被调用！！！");
            logger.info("-----<call feign)");
            return customer;
        }catch (Exception e){
            e.printStackTrace();
            return new Customer();
        }
    }

    @Override
    public HashMap<String, Object>
    findUserAndCustomerById(Long id) {
        try {
            Customer customer = this.customerFeignHystrixClient.findCustomerById(id);
//            User user = userRepository.findOne(id);
            User user = this.userFeignHystrixClient.findUserById(id);
            HashMap<String, Object> map = new HashMap<>();
            map.put("customer",customer);
            map.put("user",user);
            System.out.println("被调用！！！");
            logger.info("-----<call feign)");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    @Override
    public List<Customer> findAll() {
        try {
            List<Customer> all = this.customerFeignHystrixClient.findCustomerAll();
            return all;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findUserById(Long id) {
        try {
            return this.userFeignHystrixClient.findUserById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new User();
        }
    }
}
