package com.upfly.service.impl;

import com.upfly.dao.UserRepository;
import com.upfly.po.User;
import com.upfly.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserForShow(String username) {
        User userInDB = getUser(username);
        User user = new User();
        BeanUtils.copyProperties(userInDB, user);
        user.setSalt(null);
        user.setUsername(null);
        user.setPassword(null);
        return user;
    }

}
