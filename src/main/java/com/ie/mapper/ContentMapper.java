package com.ie.mapper;

import com.ie.pojo.Content;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ContentMapper {

    /**
     * 根据Typeid和userid获取Content
     * @param typeId 类型ID
     * @param userId 用户ID
     * @return 内容
     */
    Content selectByTypeIdAndUserId(@Param("typeId") int typeId,@Param("userId") int userId);

    List<Content> selectByTypeId(int typeId);

    List<Map<String,Object>> selectTableContentByTypeId(int typeId);

    /**
     * 获取已填写人数
     */
    int getYesInputNumByTypeId(int typeId);

    int deleteByPrimaryKey(Integer id);

    int insert(Content record);

    int insertSelective(Content record);

    Content selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Content record);

    int updateByPrimaryKeyWithBLOBs(Content record);

    int updateByPrimaryKey(Content record);
}