package com.yuan.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author yuan
 * @date 2020/7/23 4:50 下午
 */
@Configuration
@ConfigurationProperties("my")
public class DemoConfig {
    private List<String> hobby;


    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }
}
