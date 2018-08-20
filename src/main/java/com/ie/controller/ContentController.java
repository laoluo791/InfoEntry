package com.ie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ie.pojo.Content;
import com.ie.pojo.Type;
import com.ie.publicmodel.Info;
import com.ie.service.ContentService;
import com.ie.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("content")
public class ContentController {

    @Autowired
    private ContentService contentService;
    @Autowired
    private TypeService typeService;

    @ResponseBody
    @RequestMapping("/getTableData")
    public String getTableDataByTypeId(Integer typeId){

        Type type = typeService.selectByPrimaryKey(typeId);
        List<Map<String, Object>> tableList = contentService.selectTableContentByTypeId(typeId);
        /**
         * data
         Type{id=1, name='cxy',
         content='[{"name":"姓名","required":true},{"name":"上午分数","required":true},{"name":"下午分数","required":true}]'}

         [{no=1, name=王作奎, content={"val0":"王作奎","val1":"90","val2":"90"}},
         {no=2, name=罗习升}, {no=3, name=成文兵}]
         */
        //开始生成数据 column , data
        //column : [ {title:"",key:""} ]
        //data : [{key1 : "",key2:""}]

        //生成根对象
        JSONArray colArr = (JSONArray) JSON.parse("[]");
        JSONArray dataArr = (JSONArray) JSON.parse("[]");
        //添加固定表头
        colArr.add(JSON.parse("{title:'用户[学号/姓名]',key:'user'}"));
        //记录type里的key
        List<String> keyList = new ArrayList<String>();
        //遍历type的content 拿出title
        JSONArray ita = (JSONArray) JSON.parse(type.getContent());
        Iterator<Object> iterator = ita.iterator();
        int i = 0;
        while (iterator.hasNext()){
            JSONObject next = (JSONObject) iterator.next();
            String name = (String) next.get("name");
            //添加进根对象
            JSONObject colObjs = (JSONObject) JSON.parse("{}");
            colObjs.put("title",name);
            String keyStr = "val"+i++;
            keyList.add(keyStr);
            colObjs.put("key",keyStr);
            colArr.add(colObjs);
        }
        //开始生成data
        for(Map<String,Object> map : tableList){
            Object no = map.get("no")+"";
            String name = map.get("name")+"";
            //生成对象
            JSONObject dataObjs = (JSONObject) JSON.parse("{}");
            //添加固定表头数据
            dataObjs.put("user","["+no+"/"+name+"]");
            //获取content动态数据
            String content = map.get("content")+"";
            JSONObject keyObj = null;
            //非空则直接添加
            if (!content.equals("null")){
                keyObj = (JSONObject) JSON.parse(content);
                for(String key : keyList){
                    dataObjs.put(key,keyObj.get(key));
                }
                dataObjs.put("hasData",true);
            }
            //为空则设置为未填写
            else{
                for(String key : keyList){
                    dataObjs.put(key,"未填写");
                }
                dataObjs.put("hasData",false);
            }
            dataArr.add(dataObjs);
        }

        //开始准备返回值
        JSONObject tableData = JSON.parseObject("{}");
        tableData.put("columns",colArr);
        tableData.put("data",dataArr);
        return tableData.toJSONString();

    }

    /**
     * 用于获取当前用户已填写的内容
     */
    @ResponseBody
    @RequestMapping("/getContentByTypeIdAndUserId")
    public String getContentByTypeIdAndUserId(Integer typeId,Integer userId){
        Content content = contentService.selectByTypeIdAndUserId(typeId, userId);
        System.out.println(content);
        //判断当前用户是否已填写
        if (content == null){
            return null;
        }
        else{
            return content.getContent();
        }
    }

    @ResponseBody
    @RequestMapping("/saveByContent")
    public Info saveByContent(Content content){
        System.out.println(content);
        //查看是否已经存在数据
        Content content1 = contentService.selectByTypeIdAndUserId(content.getType(), content.getUser());
        int i = 0 ;
        Info info = new Info(0, "保存失败");
        //不存在数据则直接添加
        if(content1 == null){
            System.out.println("不存在 我开始直接添加");
            i = contentService.insertSelective(content);
        }
        //存在数据则更新数据
        else{
            System.out.println("存在 我开始更新");
            content1.setContent(content.getContent());
            i = contentService.updateByPrimaryKeySelective(content1);
        }
        if(i > 0){
            info.setState(1);
            info.setMsg("保存成功");
        }
        return  info;
    }

}




