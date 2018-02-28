package com.tgw360.mapper;

import com.tgw360.entity.Bar;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by 易弘博 on 2018/1/31 15:04
 */
public interface BarMapper {

    List<Bar> findByCodeAndDate(@Param("code") String code, @Param("begin")Date begin,@Param("end")Date end);
}
