package com.tgw360.repository;

import com.tgw360.entity.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account,Long> {
    public Account findByUserName(String username);
}
