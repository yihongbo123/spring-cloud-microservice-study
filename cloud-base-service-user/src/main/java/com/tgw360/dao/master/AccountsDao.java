package com.tgw360.dao.master;

import com.tgw360.domain.Accounts;
import com.tgw360.domain.User;

import java.util.List;

//@Mapper
public interface AccountsDao {

    List<Accounts> findAll();
}
