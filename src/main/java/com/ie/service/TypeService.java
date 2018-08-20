package com.ie.service;

import com.ie.pojo.Type;

import java.util.List;

public interface TypeService {

    int insertSelective(Type record);

    Type selectByName(String name);

    String getTableData();

    Type selectByPrimaryKey(Integer id);

    List<Type> selectAll();


    int deleteByPrimaryKey(Integer id);

    int deleteTypeAndContentByTypeId(Integer id);
}
