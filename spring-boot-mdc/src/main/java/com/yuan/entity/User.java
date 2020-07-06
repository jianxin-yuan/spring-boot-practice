package com.yuan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author yuan
 * @date 2020/6/22 4:23 下午
 */
@Data
@AllArgsConstructor
public class User {
    private String name;
    private Integer age;
    private Date birthDay;

}
