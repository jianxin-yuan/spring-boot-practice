package com.yuan.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author yuan
 * @date 2020/7/23 3:03 下午
 */
@Component
@Slf4j
@Order(-1)
public class FirstTask implements CommandLineRunner {
    @Value("${app.secret}")
    private String appSecret;
    @Value("${app.id}")
    private String appId;
    @Value("${server.address}")
    private String serverAddress;

    @Value("${test.name}")
    private String testName;

    @Override
    public void run(String... args) throws Exception {
        log.info("this is first task,appSecret=" + appSecret+", appId="+appId);
        log.info("server.address={}",serverAddress);
        log.info("testName={}",testName);
    }

}
