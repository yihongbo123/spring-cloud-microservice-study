package com.tgw360.mapper;

import com.tgw360.entity.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 易弘博 on 2018/2/7 10:06
 */
public interface UsersMapper {
//    public List<Users> fingByLimit(@Param("start") Integer start, @Param("num") Integer num);
    public List<Users> fingByLimit();
}
