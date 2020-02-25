package com.upfly.service;

import com.upfly.po.User;

public interface UserService {

    public User checkUser(String username, String password);

}
