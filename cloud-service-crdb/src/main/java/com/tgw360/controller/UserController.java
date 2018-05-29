package com.tgw360.controller;

import com.tgw360.entity.Accounts;
import com.tgw360.entity.User;
import com.tgw360.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
//    @Value("${aaa.aaa.aaa}")
    private String aaa;
//    @Value("${bbb.bbb.bbb}")
    private String bbb;

    @GetMapping("/save")
    public String saveUser(User user){
        try {
            boolean b = userService.save(user);

            if (b){
                return "success";
            }
            return "fail";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    @GetMapping("/getAll")
    public List<User> getAll(){
        return userService.getAll();
    }


    @GetMapping("/aaa")
    public String testA(){
        return aaa;
    }

    @GetMapping("/bbb")
    public String testB(){
        return bbb;
    }

}
