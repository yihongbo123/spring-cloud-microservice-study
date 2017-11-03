package com.tgw360;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 通过@EnableConfigServer注解激活配置服务.
 * 说明：
 * 在application.yml中有个git.uri的配置，目前配置的是https://github.com/eacdy/spring-cloud-study/
 * 获取git上的资源信息遵循如下规则：
 * /{application}/{profile}[/{label}]
 * /{application}-{profile}.yml
 * /{label}/{application}-{profile}.yml
 * /{application}-{profile}.properties
 * /{label}/{application}-{profile}.properties
 *
 * 例如本例：可使用以下路径来访问microservice-config-client-dev.properties：
 * http://localhost:8040/microservice-config-client-dev.properties
 * http://localhost:8040/microservice-config-client/dev
 * ...
 * @author eacdy
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(ConfigServerApplication.class, args);
//    }
    public static void main(String[] args) throws IOException {
        InputStream in = ConfigurationProperties.class.getResourceAsStream("/aa.properties");
        Properties properties = new Properties();
        properties.load(in);
        String aa = properties.getProperty("aa");
        System.out.println(aa);


        System.out.println(ConfigServerApplication.class.getResource("/" +
                ""));
    }
}
