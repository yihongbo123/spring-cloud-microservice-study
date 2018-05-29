package com.tgw360.common;

import java.io.Serializable;

/**
 * Created by 易弘博 on 2018/3/12 10:35
 */
public class RedisBean <T>{
    private String key;
    private T t;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
