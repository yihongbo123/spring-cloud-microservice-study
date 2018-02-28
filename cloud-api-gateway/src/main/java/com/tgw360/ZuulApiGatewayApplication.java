package com.tgw360;

import com.tgw360.filter.ErrorFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用@EnableZuulProxy注解激活zuul。
 * 跟进该注解可以看到该注解整合了@EnableCircuitBreaker、@EnableDiscoveryClient，是个组合注解，目的是简化配置。
 * @author eacdy
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@RestController
public class ZuulApiGatewayApplication {
    @Value("${aa.aa}")
    private String aa;
//    @Bean
//    public IRule ribbonRule() {
//
//        return new RoundRobinRule();//这里配置策略，和配置文件对应
//    }

//    @Bean
//    public AccessTokenFilter accessTokenFilter(){
//        return new AccessTokenFilter();
//    }
//    @Bean
//    public ErrorFilter errorFilter(){
//        return new ErrorFilter();
//    }


    public static void main(String[] args) {
        SpringApplication.run(ZuulApiGatewayApplication.class, args);
    }

    @RefreshScope
    @ConfigurationProperties("zuul")
    public ZuulProperties zuulProperties(){
        return new ZuulProperties();
    }

    @RequestMapping("aa")
    public String aa(){
        return aa;
    }
}