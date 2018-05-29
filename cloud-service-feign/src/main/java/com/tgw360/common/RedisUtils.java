package com.tgw360.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import redis.clients.jedis.*;
import redis.clients.util.JedisClusterCRC16;

import java.util.*;
import java.util.function.Supplier;

/**
 * Created by 易弘博 on 2018/1/17 15:09
 */

public class RedisUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
    public static Map<String, JedisPool> getNodeMap(String clusterNodes){
        String[] split = clusterNodes.split(",");
        HashSet<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        for (String s : split) {
            String[] split1 = s.split(":");
            HostAndPort a1 = new HostAndPort(split1[0],Integer.parseInt(split1[1]));
            hostAndPorts.add(a1);
        }
        JedisCluster jedisCluster  =  new JedisCluster(hostAndPorts);
        Map<String, JedisPool> nodeMap = jedisCluster.getClusterNodes();
        return nodeMap;
    }
    public static TreeMap<Long, String> getslotHostMap(Map<String, JedisPool> nodeMap){
        String anyHost = nodeMap.keySet().iterator().next();
        //获取所有集群节点对应的槽范围
        TreeMap<Long, String> slotHostMap = getSlotHostMap(anyHost);
        return slotHostMap;
    }
    public static Jedis getJedisClient(String key,String clusterNodes) {
        Map<String, JedisPool> nodeMap = getNodeMap(clusterNodes);
        TreeMap<Long, String> slotHostMap = getslotHostMap(nodeMap);
        //根据要插入的key知道这个key所对应的槽的号码, 再通过这个槽的号码从集群中找到对应Jedis
        //获取槽号
        int slot = JedisClusterCRC16.getSlot(key);
        Map.Entry<Long, String> entry = slotHostMap.lowerEntry(Long.valueOf(slot));
        //获取到对应的Jedis对象
        Jedis jedis = nodeMap.get(entry.getValue()).getResource();
        return jedis;
    }

    /**
     * 获取所有key对应的jedis
     * @param keys
     * @param clusterNodes
     * @return
     */
    public static Map<Jedis,List<String>> getSome(List<String> keys,String clusterNodes){
        Map<Jedis, List<String>> map = new HashMap<>();
        for (String key : keys) {
            Jedis jedisClient = getJedisClient(key,
                    clusterNodes);
            List list = map.get(jedisClient);
            if (list != null){
                list.add(key);
            }else{
                List<String> l = new ArrayList<>();
                l.add(key);
                map.put(jedisClient,l);
            }
        }
        return map;
    }

    /**
     * zrange方法get方法
     * @param redisTemplate
     * @param key
     * @param clazz
     * @param start
     * @param end
     * @param supplier
     * @param <T>
     * @return
     */
    public  static <T> List<T> zrange(RedisTemplate<String,String> redisTemplate, String key, Class<T> clazz , long start, long end, Supplier<List<T>> supplier){
        Set<String> set = null;
        try {
            ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
            //从redis中查询数据
            set = opsForZSet.range(key, start, end);
        }catch (Exception e){
            e.printStackTrace();
        }
        //判断查询value是否为null,如果为null说明redis中不存在，则进行其他操作，具体的方法自行实现
        if (set != null && set.size() > 0){
            try {
                List<T> list = new ArrayList<>();
                for (String string : set){
                    T t = OBJECT_MAPPER.readValue(string, clazz);
                    list.add(t);
                }
                return list;
            } catch (Exception e) {
                //发生异常返回null
                e.printStackTrace();
                return null;
            }
        }
        return supplier.get();
        //查询到redis中的数据并返回
    }

    private static TreeMap<Long, String> getSlotHostMap(String anyHostAndPortStr) {
        TreeMap<Long, String> tree = new TreeMap<Long, String>();
        String parts[] = anyHostAndPortStr.split(":");
        HostAndPort anyHostAndPort = new HostAndPort(parts[0], Integer.parseInt(parts[1]));
        try{
            Jedis jedis = new Jedis(anyHostAndPort.getHost(), anyHostAndPort.getPort());
            List<Object> list = jedis.clusterSlots();
            for (Object object : list) {
                List<Object> list1 = (List<Object>) object;
                List<Object> master = (List<Object>) list1.get(2);
                String hostAndPort = new String((byte[]) master.get(0)) + ":" + master.get(1);
                tree.put((Long) list1.get(0), hostAndPort);
                tree.put((Long) list1.get(1), hostAndPort);
            }
            jedis.close();
        }catch(Exception e){
            logger.error("");
        }
        return tree;
    }
    public static HashMap<HostAndPort, Jedis> getSlot(String clusterNodes){
        Map<String, JedisPool> nodeMap = getNodeMap(clusterNodes);
        List<Object> slotHostObject = getslotHostObject(nodeMap);
        HashMap<HostAndPort, Jedis> map = new HashMap<>();
        for (Object object : slotHostObject) {
            List<Object> list1 = (List<Object>) object;
            List<Object> master = (List<Object>) list1.get(2);
            Jedis jedis = new Jedis(new String((byte[]) master.get(0)), Integer.parseInt(master.get(1).toString()));
            HostAndPort hostAndPort1 = new HostAndPort(((Long) list1.get(0)).toString(), Integer.parseInt(((Long) list1.get(1)).toString()));
            map.put(hostAndPort1,jedis);
        }
        return map;
    }


    /**
     * 批量添加大量key的zset方法
     * @param list
     * @param clusterNodes
     */
    public static void zAddMuchMoreKey(String clusterNodes,List<RedisBean<ValueAndScore>> list){
        HashMap<Jedis, List<RedisBean<ValueAndScore>>> listAndJedis = getListAndJedis(list, clusterNodes);
        for (Map.Entry<Jedis, List<RedisBean<ValueAndScore>>> jedisListEntry : listAndJedis.entrySet()) {
            Jedis key = jedisListEntry.getKey();
            List<RedisBean<ValueAndScore>> value = jedisListEntry.getValue();
            Pipeline pipelined = key.pipelined();
            for (RedisBean<ValueAndScore> s : value) {
                pipelined.zadd(s.getKey(),s.getT().getScore(),s.getT().getValue());
            }
            pipelined.sync();
        }
    }
    /**
     * 批量添加大量key的hash方法
     * @param list
     * @param clusterNodes
     */
    public static void hAddMuchMoreKey(String clusterNodes,List<RedisBean<Entry>> list){
        HashMap<Jedis, List<RedisBean<Entry>>> listAndJedis = getListAndJedis(list, clusterNodes);
        for (Map.Entry<Jedis, List<RedisBean<Entry>>> jedisListEntry : listAndJedis.entrySet()) {
            Jedis key = jedisListEntry.getKey();
            List<RedisBean<Entry>> value = jedisListEntry.getValue();
            Pipeline pipelined = key.pipelined();
            for (RedisBean<Entry> s : value) {
                pipelined.hset(s.getKey(),s.getT().getKey(),s.getT().getValue());
            }
            pipelined.sync();
        }
    }

    /**
     * 批量删除大量key的zset方法
     * @param list
     * @param clusterNodes
     */
    public static void zDelMuchMoreKey(String clusterNodes,List<RedisBean<ValueAndScore>> list){
        HashMap<Jedis, List<RedisBean<ValueAndScore>>> listAndJedis = getListAndJedis(list, clusterNodes);
        for (Map.Entry<Jedis, List<RedisBean<ValueAndScore>>> jedisListEntry : listAndJedis.entrySet()) {
            Jedis key = jedisListEntry.getKey();
            List<RedisBean<ValueAndScore>> value = jedisListEntry.getValue();
            Pipeline pipelined = key.pipelined();
            for (RedisBean<ValueAndScore> s : value) {
                pipelined.del(s.getKey());
            }
            pipelined.sync();
        }
    }

    /**
     * 批量删除大量key的hash方法
     * @param list
     * @param clusterNodes
     */
    public static void hDelMuchMoreKey(String clusterNodes,List<RedisBean<Map.Entry<String,String>>> list){
        HashMap<Jedis, List<RedisBean<Map.Entry<String, String>>>> listAndJedis = getListAndJedis(list, clusterNodes);
        for (Map.Entry<Jedis, List<RedisBean<Map.Entry<String, String>>>> jedisListEntry : listAndJedis.entrySet()) {
            Jedis key = jedisListEntry.getKey();
            List<RedisBean<Map.Entry<String, String>>> value = jedisListEntry.getValue();
            Pipeline pipelined = key.pipelined();
            for (RedisBean<Map.Entry<String, String>> s : value) {
                pipelined.hdel(s.getKey(),s.getT().getKey());
            }
            pipelined.sync();
        }
    }

    public static <T> HashMap<Jedis, List<RedisBean<T>>> getListAndJedis(List<RedisBean<T>> keyAndValueAndScores,String clusterNodes) {
        HashMap<HostAndPort, Jedis> slot = getSlot(clusterNodes);
        HashMap<Map.Entry<HostAndPort, Jedis>, List<RedisBean<T>>> map = new HashMap<>();
        Set<Map.Entry<HostAndPort, Jedis>> entries = slot.entrySet();
        //把所有槽区间的jedis作为key,所有key属于这个jedis的RedisBean作为value 存入一个hashmap中
        for (Map.Entry<HostAndPort, Jedis> hostAndPortStringEntry : entries) {
            map.put(hostAndPortStringEntry,new ArrayList<RedisBean<T>>());
        }
        HashMap<Jedis, List<RedisBean<T>>> jedisListHashMap = new HashMap<>();
        for (Map.Entry<Map.Entry<HostAndPort, Jedis>, List<RedisBean<T>>> entryListEntry : map.entrySet()) {
            for (RedisBean<T> string : keyAndValueAndScores) {
                //通过传入的key判断这个key属于redis的哪个槽
                int slot1 = JedisClusterCRC16.getSlot(string.getKey());
                //判断当前槽属于哪个jedis
                if(slot1>=Integer.parseInt(entryListEntry.getKey().getKey().getHost()) && slot1<=entryListEntry.getKey().getKey().getPort()){
                    List<RedisBean<T>> value = entryListEntry.getValue();
                    value.add(string);
                }
            }
            jedisListHashMap.put(entryListEntry.getKey().getValue(),entryListEntry.getValue());
        }
        return jedisListHashMap;
    }

    private static List<Object> getslotHostObject(Map<String, JedisPool> nodeMap) {
        String anyHost = nodeMap.keySet().iterator().next();
        TreeMap<Long, String> tree = new TreeMap<Long, String>();
        String parts[] = anyHost.split(":");
        HostAndPort anyHostAndPort = new HostAndPort(parts[0], Integer.parseInt(parts[1]));
        List<Object> list = null;
        try{
            Jedis jedis = new Jedis(anyHostAndPort.getHost(), anyHostAndPort.getPort());
            list = jedis.clusterSlots();
            jedis.close();
        }catch(Exception e){

        }
        return list;
    }
    public  static  String getValue(RedisTemplate<String,String> redisTemplate, String key) {
        String value = new String();
        try {
            ValueOperations<String, String> opsForString = redisTemplate.opsForValue();
            //从redis中查询数据
            value = opsForString.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    /**
     * 分布式redis批量插入zadd方法
     * @param clusterNodes
     * @return
     */
    public static boolean zSetBatch(String clusterNodes, String key, List<ValueAndScore> list){//String key,List<ValueAndScore> valueAndScores
        try {
            Pipeline pipelined = null;
            for (ValueAndScore valueAndScore : list) {
                Jedis jedisClient = getJedisClient(key,clusterNodes);
                pipelined = jedisClient.pipelined();
                pipelined.zadd(key,valueAndScore.getScore(),valueAndScore.getValue());
            }
            pipelined.sync();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void zSetBatch(String clusterNodes, String s, ArrayList<ValueAndScore> list) {

    }
}
