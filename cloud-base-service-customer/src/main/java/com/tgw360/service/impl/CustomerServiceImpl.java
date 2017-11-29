package com.tgw360.service.impl;

import com.tgw360.common.redisUtil;
import com.tgw360.entity.Customer;
import com.tgw360.repository.CustomerRepository;
import com.tgw360.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private CustomerRepository customerRepository;

    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public List<Customer> findAll() {
        return redisUtil.getValue(redisTemplate, "customer-all", key -> {
            List<Customer> customers = customerRepository.findAll();
            redisUtil.setValue(redisTemplate,key,customers,7,TimeUnit.DAYS);
            return customers;
        });
    }
//    @Override
//    public List<Customer> findAll() {
//        return redisUtil.getValue(redisTemplate, "customer-all", new Function<String, List<Customer>>() {
//            @Override
//            public List<Customer> apply(String key) {
//                List<Customer> customers = customerRepository.findAll();
//                redisUtil.setValue(redisTemplate,key,customers);
//                return customers;
//            }
//        });
//    }
//    @Override
//    public List<Customer> findAll() {
//        String value = null;
//        ValueOperations<String, String> opsForValue = null;
//        try {
//            opsForValue = redisTemplate.opsForValue();
//            //从redis中查询数据
//            value = opsForValue.get("customer-all");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        //判断redis中是否存在缓存，如果不存在，查询mysql
//        if (value == null){
//            List<Customer> all = customerRepository.findAll();
//            try {
//                String valueAsString = objectMapper.writeValueAsString(all);
//                opsForValue.set("customer-all",valueAsString);
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.info("存入redis缓存失败");
//            }
//            return all;
//        }
//        try {
//            List<Customer> customers = objectMapper.readValue(value, new TypeReference<List<Customer>>() {});
//            return customers;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<Customer>();
//        }
//    }

    @Override
    public Customer findByUsernameAndPassword(String username, String password) {
        return redisUtil.getValue(redisTemplate,"customer-"+username+password,Customer.class,key ->{
            Customer customer = customerRepository.findByUsernameAndPassword(username, password);
            redisUtil.setValue(redisTemplate,key,customer,10,TimeUnit.DAYS);
            return customer;
        });

    }

    @Override
    public Customer findById(Long id) {
        return redisUtil.getValue(redisTemplate,"customer-" + id,Customer.class,key -> {
            Customer customer = customerRepository.findOne(id);
            redisUtil.setValue(redisTemplate,key,customer,10, TimeUnit.DAYS);
            return customer;
        });
//        return customerRepository.findOne(id);


    }
}
