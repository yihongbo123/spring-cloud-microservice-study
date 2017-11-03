package com.tgw360.hystrixclient;

import com.tgw360.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用@FeignClient注解的fallback属性，指定fallback类
 * @author eacdy
 */
@FeignClient(name = "cloud-base-service-customer", fallback = CustomerFeignHystrixClient.CustomerHystrixClientFallback.class)
public interface CustomerFeignHystrixClient {
    @RequestMapping(value = "/customer/{id}",method = RequestMethod.GET)
    public Customer findCustomerById(@RequestParam("id") Long id);

    @RequestMapping(value = "/customer/all",method = RequestMethod.GET)
    public List<Customer> findCustomerAll();
    /**
     * 这边采取了和Spring Cloud官方文档相同的做法，将fallback类作为内部类放入Feign的接口中，当然也可以单独写一个fallback类。
     *
     */

    @Component
    static class CustomerHystrixClientFallback implements CustomerFeignHystrixClient {
        private static final Logger LOGGER = LoggerFactory.getLogger(CustomerHystrixClientFallback.class);

        /**
         * hystrix fallback方法
         * @param id id
         * @return 默认的用户
         */
        @Override
        public Customer findCustomerById(Long id) {
            CustomerHystrixClientFallback.LOGGER.info("异常发生，进入fallback方法，接收的参数：id = {}", id);
            Customer customer = new Customer();
            return customer;
        }

        /**
         * hystrix fallback方法
         * @return 默认的用户
         */
        @Override
        public List<Customer> findCustomerAll() {
            CustomerHystrixClientFallback.LOGGER.info("异常发生，进入fallback方法");
            ArrayList<Customer> customers = new ArrayList<Customer>();
            return customers;
        }
    }
}
