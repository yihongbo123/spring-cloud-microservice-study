package com.tgw360.entity.repository;

import com.tgw360.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by 易弘博 on 2018/3/7 13:31
 */
public interface ArticleSearchRepository extends ElasticsearchRepository<Article, Long> {
}
