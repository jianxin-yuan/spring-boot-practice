package com.yuan.controller;

import com.yuan.entity.User;
import com.yuan.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author yuan
 * @date 2020/7/3 9:44 上午
 */
@RestController
@Log4j2
public class TraceController {

    @Autowired
    private UserService userService;

    /**
     * 简单线程跟踪
     *
     * @param id
     * @return
     */
    @RequestMapping("/simple")
    public User simpleTrace(@RequestParam("id") Long id) {
        log.debug("简单线程traceId跟踪...,params={}", id);
        User user = userService.getUserById(id);
        log.info("返回结果={}", user);
        return user;
    }

    /**
     * 子线程跟踪
     */
    @RequestMapping("/thread")
    public String childThread(@RequestParam("id") Long id) {
        log.debug("子线程traceId跟踪...,params={}", id);
        User user = userService.getUserById(id);

        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        new Thread(() -> {
            MDC.setContextMap(contextMap);
            userService.updateUser(user);
        }).start();

        return "ok";
    }


    /**
     * 线程池跟踪
     *
     * @return
     */
    @RequestMapping("/pool")
    public String threadPool() {
        log.debug("线程池traceId跟踪...start...");
        userService.addUser();
        log.debug("线程池traceId跟踪...end...");
        return "ok";
    }
}
