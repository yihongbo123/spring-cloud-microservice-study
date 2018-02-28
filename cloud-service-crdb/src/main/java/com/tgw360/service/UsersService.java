package com.tgw360.service;

import com.tgw360.entity.Users;
import com.tgw360.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 易弘博 on 2018/2/7 10:10
 */
@Service
public class UsersService {
    @Autowired
    private UsersMapper usersMapper;
    public List<Users> findByLimit(Integer start, Integer num){
        List<Users> users = usersMapper.fingByLimit();
//        List<Users> users = usersMapper.fingByLimit(start, num);
        return users;
    }
}
