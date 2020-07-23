package com.yuan.repository;

import org.springframework.stereotype.Repository;

/**
 * @author yuan
 * @date 2020/7/23 11:17 上午
 */
@Repository
public class DemoRepository {
    public String injectDemo() {
        return "inject demo";
    }
}
