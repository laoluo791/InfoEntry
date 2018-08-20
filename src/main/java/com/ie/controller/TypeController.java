package com.ie.controller;

import com.ie.pojo.Type;
import com.ie.publicmodel.Info;
import com.ie.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @ResponseBody
    @RequestMapping("getAllType")
    public List<Type> getAllType(){
        List<Type> types = typeService.selectAll();
        return types;
    }

    /**
     * 获取格式化后的table数据
     * @return
     */
    @ResponseBody
    @RequestMapping("getTableData")
    public String getTableData(){
        String datas = typeService.getTableData();
        return datas;
    }

    @ResponseBody
    @RequestMapping("/addType")
    public Info addType(Type type){
        System.out.println(type);
        //需要判断当前名称是否已存在 , 名称需要唯一
        Type type1 = typeService.selectByName(type.getName());
        Info info = new Info(0, "添加失败");
        if (type1 != null){
            info.setMsg("添加失败,项目名重复");
            return info;
        }
        //添加
        int i = typeService.insertSelective(type);
        if(i > 0){
            info.setState(1);
            info.setMsg("添加成功");
        }
        return  info;
    }

    /**
     * 根据项目名返回项目需要填写整个项目信息
     */
    @ResponseBody
    @RequestMapping("getTypeByName")
    public Type getTypeByName(String cateName){
        System.out.println(cateName);
        Type type = typeService.selectByName(cateName);
        return type;
    }


    /**
     * 根据id返回项目信息
     */
    @ResponseBody
    @RequestMapping("getTypeById")
    public Type getTypeById(Integer typeId){
        Type type = typeService.selectByPrimaryKey(typeId);
        return type;
    }


    /**
     * 根据id删除 , 同时删除项目的内容
     */
    @ResponseBody
    @RequestMapping("deleteTypeAndContentByTypeId")
    public Info deleteByPrimaryKey(Integer typeId){
        int i = typeService.deleteTypeAndContentByTypeId(typeId);
        Info info = new Info(0, "删除失败");
        if(i > 0){
            info.setState(1);
            info.setMsg("删除成功");
        }
        return  info;
    }

}
