package com.upfly.service;

import com.upfly.po.User;

public interface UserService {

    public User getUser(String username);

    // 传给前端页面的user(包含部分信息)
    public User getUserForShow(String username);

}
