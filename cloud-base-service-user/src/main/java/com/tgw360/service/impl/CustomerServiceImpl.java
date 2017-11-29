package com.tgw360.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgw360.dao.master.CustomerDao;
import com.tgw360.domain.User;
import com.tgw360.service.CustomerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private CustomerDao customerDao;
    @Override
    public User getCustomerByAccountId(Long id) {
        logger.info("-----<call user>-----");
        String s = null;
        ValueOperations<String, String> ops = null;
        try {
            ops = redisTemplate.opsForValue();
            s = ops.get("user-" + id);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (s == null){
            User customer = customerDao.findByAccountId(id);
            try {
                String cust = objectMapper.writeValueAsString(customer);
//                ops.set("customer" + id,cust,10, TimeUnit.SECONDS);
                ops.set("user-" + id,cust);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return customer;
        }
        try {
            User customer = objectMapper.readValue(s, User.class);
            return customer;
        } catch (Exception e) {
            e.printStackTrace();
            return new User();
        }
//        return customerDao.findByAccountId(id);
    }

    @Override
    public List<User> getAll() {
        return customerDao.findAll();
    }

}
