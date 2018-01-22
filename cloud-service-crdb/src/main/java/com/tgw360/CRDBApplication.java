package com.tgw360;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
public class CRDBApplication {
    public static void main(String[] args) {
        SpringApplication.run(CRDBApplication.class,args);
    }

}
