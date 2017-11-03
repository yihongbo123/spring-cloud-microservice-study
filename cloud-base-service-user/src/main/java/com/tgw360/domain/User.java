package com.tgw360.domain;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.LastModified;

import java.lang.management.ManagementFactory;

public class User {
    private Long id;
    private Long createdAt;
    private Long LastModified;
    private String email;
    private String firstName;
    private String lastName;
    private Long accountId;


    public Long getId() {
        return id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getLastModified() {
        return LastModified;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastModified(Long lastModified) {
        LastModified = lastModified;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
