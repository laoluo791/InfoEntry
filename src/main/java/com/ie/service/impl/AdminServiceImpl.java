package com.ie.service.impl;

import com.ie.mapper.AdminMapper;
import com.ie.pojo.Admin;
import com.ie.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public boolean login(Admin admin) {
        return adminMapper.login(admin);
    }
}
