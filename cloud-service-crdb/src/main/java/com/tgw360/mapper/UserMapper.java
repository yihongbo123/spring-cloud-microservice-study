package com.tgw360.mapper;

import com.tgw360.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    public boolean save(@Param("user") User user);
    public List<User> findAll();
    public User findById(Long id);
}
