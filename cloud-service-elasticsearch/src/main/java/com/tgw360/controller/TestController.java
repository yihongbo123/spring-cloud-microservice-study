package com.tgw360.controller;

import com.tgw360.entity.Article;
import com.tgw360.entity.Author;
import com.tgw360.entity.Tutorial;
import com.tgw360.entity.repository.ArticleSearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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



}
