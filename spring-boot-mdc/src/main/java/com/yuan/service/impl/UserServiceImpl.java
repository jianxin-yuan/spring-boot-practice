package com.yuan.service.impl;

import com.yuan.entity.User;
import com.yuan.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author yuan
 * @date 2020/7/3 9:57 上午
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public User getMockUser() {
        log.debug("获取用户信息...");
        return new User("yuan", 22, new Date());
    }

    @Override
    public void updateUser(User user) {
        log.debug("子线程更新数据,params={}", user);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("子线程更新数据完成");
    }

    @Override
    @Async
    public void addUser() {
        log.debug("线程池更新数据...");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("线程池更新数据完成...");
    }
}
