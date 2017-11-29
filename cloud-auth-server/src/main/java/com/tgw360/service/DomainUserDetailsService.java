package com.tgw360.service;

import com.tgw360.entity.Account;
import com.tgw360.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DomainUserDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;    // 账户数据操作接口

    /**
     * 根据用户名查找账户信息并返回用户信息实体
     * @param username 用户名
     * @return 用于身份认证的 UserDetails 用户信息实体
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserName(username);
        if (account!=null){
            return new User(account.getUserName(),account.getPassWord(), AuthorityUtils.createAuthorityList(new String[]{"ROLE_ADMIN","ROLE_USER"}));
        }else {
            throw  new UsernameNotFoundException("用户["+username+"]不存在");
        }
    }




}
