package com.tgw360.user.service;

import com.tgw360.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class RibbonHystrixService {
    @Autowired
    private RestTemplate restTemplate;
//    @Autowired
//    private LoadBalancerClient loadBalancerClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(RibbonHystrixService.class);

    /**
     * 使用@HystrixCommand注解指定当该方法发生异常时调用的方法
     * @param id id
     * @return 通过id查询到的用户
     */
    @HystrixCommand(fallbackMethod = "fallback"
//                    ,commandProperties = {@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="5")
//                    }

    )
    public Customer findById(Long id) {
//        return this.restTemplate.postForObject("http://microservice-provider-user/" + id,null, Customer.class);
        //选择负载均衡策略
//        this.loadBalancerClient.choose("service-B");
        return this.restTemplate.getForObject("http://microservice-provider-user/" + id, Customer.class);
    }

    /**
     * hystrix fallback方法
     * @param id id
     * @return 默认的用户
     */
    public Customer fallback(Long id) {
        RibbonHystrixService.LOGGER.info("异常发生，进入fallback方法，接收的参数：id = {}", id);
        Customer customer = new Customer();
        return customer;
    }
}