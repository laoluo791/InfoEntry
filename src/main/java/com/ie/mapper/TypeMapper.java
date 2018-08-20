package com.ie.mapper;

import com.ie.pojo.Type;

import java.util.List;

public interface TypeMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteTypeAndContentByTypeId(Integer id);

    int insert(Type record);

    int insertSelective(Type record);

    Type selectByPrimaryKey(Integer id);

    Type selectByName(String name);

    List<Type> selectAll();

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKeyWithBLOBs(Type record);

    int updateByPrimaryKey(Type record);
}