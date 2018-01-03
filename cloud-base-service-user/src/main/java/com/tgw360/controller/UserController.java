package com.tgw360.controller;

import com.netflix.discovery.converters.Auto;
import com.tgw360.domain.Accounts;
import com.tgw360.domain.User;
import com.tgw360.service.AccountsService;
import com.tgw360.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


/**
 * 作用：
 * ① 测试服务实例的相关内容
 * ② 为后来的服务做提供
 * @author eacdy
 */
@RestController
@RefreshScope
public class UserController {
//    @Autowired
//    private DiscoveryClient discoveryClient;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountsService accountsService;

    @Value("${aa.value}")
    private String num;



    /**
     * 注：@GetMapping("/{id}")是spring 4.3的新注解等价于：
     * @RequestMapping(value = "/id", method = RequestMethod.GET)
     * 类似的注解还有@PostMapping等等
     * @param id
     * @return user信息
     */
//    @GetMapping("/{id}")
//    public User findById(@PathVariable Long id) {
//        User findOne = this.userRepository.findOne(id);
//        return findOne;
//    }
    @GetMapping("/{id}")
//    @PostMapping("/{id}")
    public User findCustomerByAccountId(@PathVariable("id") Long id){
        System.out.println("被调用:" + UUID.randomUUID().toString());
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//        }
        return customerService.getCustomerByAccountId(id);
    }

    @GetMapping("/Accounts")
    public List<Accounts> getAccounts(){
        return accountsService.getAll();
    }

    @GetMapping("/all")
    public List<User> findAll(){

        return customerService.getAll();
    }

    @GetMapping("/num")
    public String getNum(){
        return num;
    }
    /**
     * 本地服务实例的信息
     * @return
     */
//    @GetMapping("/instance-info")
//    public ServiceInstance showInfo() {
//        ServiceInstance localServiceInstance = this.discoveryClient.getLocalServiceInstance();
//        return localServiceInstance;
//    }

}
