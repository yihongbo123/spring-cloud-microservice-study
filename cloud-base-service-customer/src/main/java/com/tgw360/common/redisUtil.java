package com.tgw360.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgw360.service.impl.CustomerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class redisUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    /**
     * redis中string存储类型的获取值的方法
     * @param redisTemplate
     * @param key redis中的键
     * @param clazz 需要返回值的类型
     * @param function 自定义实现的方法
     * @param <T>
     * @param <K>
     * @return
     */
    public  static <T,K> T getValue(RedisTemplate<K,String> redisTemplate, K key,Class<T> clazz ,Function<K,T> function){
        String value = null;
        try {
            ValueOperations<K, String> opsForValue = redisTemplate.opsForValue();
            //从redis中查询数据
            value = opsForValue.get(key);
            logger.info("查询redis缓存数据，key={},value={}",key,value);
        }catch (Exception e){
            e.printStackTrace();
        }
        //判断查询value是否为null,如果为null说明redis中不存在，则进行其他操作，具体的方法自行实现
        if (value == null){
            return function.apply(key);
        }
        //查询到redis中的数据并返回
        try {
                return objectMapper.readValue(value, clazz);
        } catch (Exception e) {
            //发生异常返回null
            System.out.println(1111);
            return null;
        }
    }

    /**
     * redis中string存储类型的获取值的方法,返回值为复杂类型（如集合List<Person>,HashMap<String,Person>）
     * @param redisTemplate
     * @param key redis中的键
     * @param function 自定义实现的方法
     * @param <T> 返回值类型
     * @param <K>
     * @return
     */
    public  static <T,K> T getValue(RedisTemplate<K,String> redisTemplate, K key ,Function<K,T> function){
        String value = null;
        try {
            ValueOperations<K, String> opsForValue = redisTemplate.opsForValue();
            //从redis中查询数据
            value = opsForValue.get(key);
            logger.info("查询redis缓存数据，key={},value={}",key,value);
        }catch (Exception e){
            e.printStackTrace();
        }
        //判断查询value是否为null,如果为null说明redis中不存在，则进行其他操作，具体的方法自行实现
        if (value == null){
            return function.apply(key);
        }
        //查询到redis中的数据并返回
        try {
            return objectMapper.readValue(value, new TypeReference<T>(){});
        } catch (Exception e) {
            //发生异常返回null
            System.out.println(1111);
            return null;
        }
    }

    /**
     * redis中string存储类型的存储值的方法
     * @param redisTemplate
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     */
    public static <K,V> void setValue(RedisTemplate<K,String> redisTemplate,K key,V value){
        ValueOperations<K, String> opsForValue = redisTemplate.opsForValue();
        try {
            String s = objectMapper.writeValueAsString(value);
            opsForValue.set(key,s);
            logger.info("设置redis缓存数据，key={},value={}",""+key,""+s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * redis中string存储类型的存储值的方法，带过期时间
     * @param redisTemplate
     * @param key
     * @param value
     * @param timeout 过期时间
     * @param unit 时间类型（比如：TimeUnit.DAYS代表日）
     * @param <K>
     * @param <V>
     */
    public static <K,V> void setValue(RedisTemplate<K,String> redisTemplate,K key,V value, long timeout, TimeUnit unit){
        ValueOperations<K, String> opsForValue = redisTemplate.opsForValue();
        try {
            String s = objectMapper.writeValueAsString(value);
            opsForValue.set(key,s,timeout,unit);
            logger.info("设置redis缓存数据，key={},value={},time={}",""+key,""+s,""+timeout+unit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
