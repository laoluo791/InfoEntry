package com.ie.mapper;

import com.ie.pojo.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    boolean login(Admin admin);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}