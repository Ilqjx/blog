package com.upfly.service.impl;

import com.upfly.dao.UserRepository;
import com.upfly.pojo.User;
import com.upfly.service.UserService;
import com.upfly.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, MD5Util.code(password));
        return user;
    }

}
