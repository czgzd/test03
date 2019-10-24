package com.sinocarbon.integral.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JackSonUtil {
	
	@Autowired
	ObjectMapper mapper;
	
	/**
	 * 
	 * @description javaBean、列表数组转换为json字符串 
	 * @param obj  	转换对象
	 * @throws Exception
	 * @return String   
	 * @author wangxiaowei 
	 * @date 2019/07/19
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
    public  String java2json(Object obj) {
    	
    	String jsonStr = null;
    	
        try {
			 jsonStr = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("jackson转换异常",e);
		}
        return jsonStr;
    }

    
    /**
     * 
     * @description json 转JavaBean 
     * @param jsonString  json串
     * @param  clazz   实体类class
     * @throws Exception
     * @return T   
     * @author wangxiaowei 
     * @date 2019/07/19
     * @modifier xxx
     * @modifyDate 修改日期
     */
    public  <T> T json2javaBean(String jsonString, Class<T> clazz)   {
    	
    	T javaBean = null;
    	
        try {
        	javaBean =  mapper.readValue(jsonString, clazz);
		} catch (Exception e) {
			log.error("jackson转换异常",e);
		} 
        
        return javaBean;
        
    }

    /**
     * 
     * @description list 转json 
     * @param list  
     * @throws Exception
     * @return String  json串 
     * @author wangxiaowei 
     * @date 2019/07/19
     * @modifier xxx
     * @modifyDate 修改日期
     */
    public <T>String list2json(List<T> list)  {
   
    	String listjson = null;
    	
        try {
        	listjson =   mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			log.error("jackson 转换异常",e);
		}
        
        return listjson;
    }
    
    /**
     * 
     * @description json转list 
     * @param json  json串
     * @param beanClass  要转换的类的class
     * @throws Exception
     * @return List<T>   
     * @author wangxiaowei 
     * @date 2019/07/19
     * @modifier xxx
     * @modifyDate 修改日期
     */
    public <T> List<T> json2list(String json,Class<T> beanClass)  {
    	
    	 JavaType jt = mapper.getTypeFactory().constructParametricType(ArrayList.class, beanClass);
    	 List<T> dataList = null;    	 
        try {
			 dataList = mapper.readValue(json, jt);
		} catch (Exception e) {
		  log.error("jackson 转换异常",e);
		}
        
        return dataList;
        

    }
    
    
}
