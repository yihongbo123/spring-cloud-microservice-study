package com.tgw360.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

/**
 * 账户实体类
 */
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;          // 主键
    private String userName;    // 用户名
    private String passWord;    // 密码
//    private String[] roles;     // 角色

    public Account(String userName, String passWord, List<GrantedAuthority> authorityList) {
    }

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

//    public String[] getRoles() {
//        return roles;
//    }
//
//    public void setRoles(String[] roles) {
//        this.roles = roles;
//    }
}
