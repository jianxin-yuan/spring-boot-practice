package com.yuan.service;

import com.yuan.entity.User;

/**
 * @author yuan
 * @date 2020/7/3 9:53 上午
 */
public interface UserService {

    User getMockUser();

    void updateUser(User user);

    void addUser();
}
