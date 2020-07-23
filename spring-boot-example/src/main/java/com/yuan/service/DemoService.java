package com.yuan.service;

import com.yuan.config.DemoConfig;
import com.yuan.repository.DemoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author yuan
 * @date 2020/7/23 11:16 上午
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DemoService {
    private final DemoRepository repository;
    private final DemoConfig demoConfig;

    public void demo() {
        log.info(repository.injectDemo());
        log.info("config is {}",demoConfig.getHobby());
    }
}
