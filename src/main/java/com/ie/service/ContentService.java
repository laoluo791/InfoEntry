package com.ie.service;

import com.ie.pojo.Content;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ContentService {

    Content selectByTypeIdAndUserId(@Param("typeId") int typeId, @Param("userId") int userId);


    List<Map<String,Object>> selectTableContentByTypeId(int typeId);

    /**
     * 获取已填写人数
     */
    int getYesInputNumByTypeId(int typeId);

    int insertSelective(Content record);

    int updateByPrimaryKeySelective(Content record);
}
