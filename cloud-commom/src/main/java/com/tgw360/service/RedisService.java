package com.tgw360.service;


import java.util.List;

public interface RedisService<K,V> {
    /**获得客户端列表 */
    public List<?> getClients();
    /**设置有超时时间的KV */
    public void set(K key, V value, long seconds);
    /**
     *存入不会超时的KV
     */
    public void set(K key, V value);
    /**
     * redis数据库条数
     */
    public Long dbSize();

    /**判断redis数据库是否有对应的key
     *
     */
    public boolean exist(K key);

    public V get(K key);
}
