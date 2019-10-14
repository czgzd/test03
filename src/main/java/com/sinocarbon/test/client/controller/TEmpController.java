package com.sinocarbon.test.client.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinocarbon.test.client.pojo.Girl;
import com.sinocarbon.test.client.pojo.TEmp;
import com.sinocarbon.test.client.service.GirlService;
import com.sinocarbon.test.client.service.TEmpService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuan123
 * @since 2019-10-11
 */
@RestController
@RequestMapping("/tEmp")
public class TEmpController 
{
	@Autowired
	private TEmpService empService;
	 
	@RequestMapping("/emp")
	@ResponseBody
	public TEmp listGirl(Integer id){
	
		return empService.selectById(id);
	}
}

