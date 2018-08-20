package com.ie.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ie.mapper.ContentMapper;
import com.ie.mapper.TypeMapper;
import com.ie.pojo.Content;
import com.ie.pojo.Type;
import com.ie.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private ContentMapper contentMapper;

    public int insertSelective(Type record){
        int i = typeMapper.insertSelective(record);
        return i;
    }

    /**
     * 获取格式化的table数据
     * @return
     */
    public String getTableData() {

        /**
         * [{
         *     id:1,
         *     name:"程序员",
         *     yusInputNum:11
         * }]
         */
        List<Type> types = typeMapper.selectAll();
        JSONArray arrays = (JSONArray) JSONArray.toJSON(types);
        for (Object obj : arrays){
            Integer id = ((JSONObject) obj).getInteger("id");
            int yesInputNumByTypeId = contentMapper.getYesInputNumByTypeId(id);
            ((JSONObject) obj).put("yesInput",yesInputNumByTypeId);
            //把content转为[姓名,分数]这样的数组
            JSONArray contents = JSON.parseArray((String) ((JSONObject) obj).get("content"));
            JSONArray ja = JSON.parseArray("[]");
            for(int i = 0 ; i < contents.size() ; i++){
                JSONObject jo = (JSONObject) contents.get(i);
                ja.add(jo.get("name"));
            }
            ((JSONObject) obj).put("content",ja);
        }

        return arrays.toJSONString();
    }

    public Type selectByName(String name) {
        return typeMapper.selectByName(name);
    }


    public Type selectByPrimaryKey(Integer id) {
        return typeMapper.selectByPrimaryKey(id);
    }

    public List<Type> selectAll() {
        return typeMapper.selectAll();
    }

    public int deleteByPrimaryKey(Integer id) {
        return typeMapper.deleteByPrimaryKey(id);
    }

    public int deleteTypeAndContentByTypeId(Integer id) {
        int num = contentMapper.getYesInputNumByTypeId(id);
        if(num > 0){
            return typeMapper.deleteTypeAndContentByTypeId(id);
        }
        else{
            return  typeMapper.deleteByPrimaryKey(id);
        }
    }
}
