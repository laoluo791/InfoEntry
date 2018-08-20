package com.ie.service.impl;

import com.ie.mapper.ContentMapper;
import com.ie.pojo.Content;
import com.ie.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentMapper contentMapper;

    public Content selectByTypeIdAndUserId(int typeId, int userId) {
        return contentMapper.selectByTypeIdAndUserId(typeId,userId);
    }

    public int getYesInputNumByTypeId(int typeId) {
        return contentMapper.getYesInputNumByTypeId(typeId);
    }

    public List<Content> selectByTypeId(int typeId){
        return contentMapper.selectByTypeId(typeId);
    }

    public List<Map<String, Object>> selectTableContentByTypeId(int typeId) {
        return contentMapper.selectTableContentByTypeId(typeId);
    }

    public int insertSelective(Content record) {
        return contentMapper.insertSelective(record);
    }

    public int updateByPrimaryKeySelective(Content record) {
        return contentMapper.updateByPrimaryKeySelective(record);
    }
}


