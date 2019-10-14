package com.sinocarbon.test.client.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinocarbon.test.client.pojo.Girl;
import com.sinocarbon.test.client.pojo.TDept;
import com.sinocarbon.test.client.service.GirlService;
import com.sinocarbon.test.client.service.TDeptService;

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
@RequestMapping("/tDept")
public class TDeptController {

	@Autowired
    private TDeptService deptService;
	
	@PostMapping(value = "/insert")
	public TDept insert(@RequestBody TDept dept) {
		return  deptService.insertDept(dept);
				
		
	}
	
}

