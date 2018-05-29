package com.tgw360.controller;

import com.tgw360.entity.Article;
import com.tgw360.entity.Author;
import com.tgw360.entity.Tutorial;
import com.tgw360.entity.repository.ArticleSearchRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.*;
import redis.clients.util.JedisClusterCRC16;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by 易弘博 on 2018/3/7 13:49
 */
@RestController
@RequestMapping("/elasticsearch")
@Slf4j
public class TestController {

    @Autowired
    private ArticleSearchRepository articleSearchRepository;

    @RequestMapping("/add")
    public void testSaveArticleIndex() {
        Author author = new Author();
        author.setId(1L);
        author.setName("yihongbo");
        author.setRemark("java developer");

        Tutorial tutorial = new Tutorial();
        tutorial.setId(1L);
        tutorial.setName("elastic search");

        Article article = new Article();
        article.setId(1L);
        article.setTitle("springboot integreate elasticsearch");
        article.setAbstracts("springboot integreate elasticsearch is very easy");
        article.setTutorial(tutorial);
        article.setAuthor(author);
        article.setContent("elasticsearch based on lucene,"
                + "spring-data-elastichsearch based on elaticsearch"
                + ",this tutorial tell you how to integrete springboot with spring-data-elasticsearch");
        article.setPostTime(new Date());
        article.setClickCount(1L);

        articleSearchRepository.save(article);
    }

    @GetMapping("/query/{queryString}")
    public List<Article> testSearch(@PathVariable("queryString") String queryString) {

        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
        Iterable<Article> searchResult = articleSearchRepository.search(builder);
        Iterator<Article> iterator = searchResult.iterator();
        List<Article> copy = new ArrayList<Article>();
        while (iterator.hasNext())
            copy.add(iterator.next());
        return copy;
    }



    @RequestMapping(value = "/testAddRedis", method = RequestMethod.GET)
    public Object testAddRedis(String message) {
        long l = System.currentTimeMillis();
        HashMap<String,Double> aa = new HashMap<String,Double>();
        for (int i = 0; i <1000000; i++) {
            aa.put(i+"",i+0.0);
        }
        Jedis jedis = testRedis("123");
        try {
            Pipeline p  = jedis.pipelined();
            for (Map.Entry<String, Double> stringDoubleEntry : aa.entrySet()) {
                p.zadd("123",stringDoubleEntry.getValue(),stringDoubleEntry.getKey());
            }
            p.sync();
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        long l1 = System.currentTimeMillis();
        long a = l-l1;

        return "success"+a;
    }


    public Jedis testRedis(String key) {
        HashSet<HostAndPort> hostAndPorts = new HashSet<HostAndPort>();
        HostAndPort a1 = new HostAndPort("172.18.44.120",6380);
        HostAndPort a2 = new HostAndPort("172.18.44.128",6380);
        HostAndPort a3 = new HostAndPort("172.18.44.123",6380);
        HostAndPort a4 = new HostAndPort("172.18.44.120",6381);
        HostAndPort a5 = new HostAndPort("172.18.44.128",6381);
        HostAndPort a6 = new HostAndPort("172.18.44.123",6381);
        hostAndPorts.add(a1);
        hostAndPorts.add(a2);
        hostAndPorts.add(a3);
        hostAndPorts.add(a4);
        hostAndPorts.add(a5);
        hostAndPorts.add(a6);
        JedisCluster jedisCluster  =  new JedisCluster(hostAndPorts);
        Map<String, JedisPool> nodeMap = jedisCluster.getClusterNodes();
        String anyHost = nodeMap.keySet().iterator().next();
        //获取所有集群节点对应的槽范围
        TreeMap<Long, String> slotHostMap = getSlotHostMap(anyHost);
        //根据要插入的key知道这个key所对应的槽的号码, 再通过这个槽的号码从集群中找到对应Jedis
        //获取槽号
        int slot = JedisClusterCRC16.getSlot(key);
        Map.Entry<Long, String> entry = slotHostMap.lowerEntry(Long.valueOf(slot));
        //获取到对应的Jedis对象
        Jedis jedis = nodeMap.get(entry.getValue()).getResource();
        return jedis;
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

        }
        return tree;
    }
}
