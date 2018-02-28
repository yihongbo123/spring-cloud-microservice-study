package com.tgw360.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tgw360.entity.Users;
import com.tgw360.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 易弘博 on 2018/2/7 10:11
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @GetMapping("/{start}/{num}")
    public PageInfo<Users> findByLimit(@PathVariable("start") Integer start, @PathVariable("num") Integer num){
        try {
            PageHelper.startPage(start, num);
            List<Users> users = usersService.findByLimit(start*num,num);
            PageInfo<Users> usersPageInfo = new PageInfo<>(users,5);
            return usersPageInfo;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
