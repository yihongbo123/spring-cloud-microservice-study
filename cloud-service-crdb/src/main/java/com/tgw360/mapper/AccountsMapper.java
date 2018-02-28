package com.tgw360.mapper;

import com.tgw360.entity.Accounts;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AccountsMapper {
    public boolean save(@Param("accounts") Accounts accounts);
    public List<Accounts> findAll();
    public Accounts findById(Integer id);

    public List<Map<String,Object>> finduser();
}
