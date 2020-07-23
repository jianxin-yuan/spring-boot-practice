package com.yuan.runner;

import com.yuan.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author yuan
 * @date 2020/7/23 3:08 下午
 */
@Component
@Slf4j
public class SecondTask implements ApplicationRunner {
    @Autowired
    private DemoService demoService;

    @Override
    public void run(ApplicationArguments args)  {
        log.info("this is second task");
        demoService.demo();
    }
}
