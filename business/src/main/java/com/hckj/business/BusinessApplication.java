package com.hckj.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class BusinessApplication {
    // 将 log 变为静态
    private static final Logger log = LoggerFactory.getLogger(BusinessApplication.class);
    public static void main(String[] args) {
        ConfigurableEnvironment environment = SpringApplication.run(BusinessApplication.class, args).getEnvironment();
        log.info("测试地址 http://localhost:{}{}/hello",environment.getProperty("server.port"),environment.getProperty("server.servlet.context-path"));
    }

}
