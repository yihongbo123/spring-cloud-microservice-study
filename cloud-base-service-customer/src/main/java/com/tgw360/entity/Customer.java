package com.tgw360.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="taoguwang360_customer")
public class Customer {
    //声明此属性为主键
    @Id
    /*指定主键的生成策略。有如下四个值
               TABLE：使用表保存id值
               IDENTITY：identitycolumn
               SEQUENCR ：sequence
               AUTO：根据数据库的不同使用上面三个
     */
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String username;
    @JsonIgnore //写出json是不携带的属性
    private String password;
    private String email;
    private String phoneNum;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
