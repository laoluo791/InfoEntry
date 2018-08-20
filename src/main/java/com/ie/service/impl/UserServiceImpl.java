package com.ie.service.impl;

import com.ie.mapper.UserMapper;
import com.ie.pojo.User;
import com.ie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }
}
