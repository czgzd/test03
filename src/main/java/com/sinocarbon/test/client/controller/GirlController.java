package com.sinocarbon.test.client.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinocarbon.test.client.pojo.Girl;
import com.sinocarbon.test.client.service.GirlService;

import java.util.List;

import javax.validation.Valid;

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
@RequestMapping("/api")
public class GirlController
{
    @Autowired
    private GirlService girlService;
    
    // @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @RequestMapping("/boy/{id}")
    // @PostMapping(value = "/user")
    public String delete(Integer id)
    {
        // return "121";
    	int result = girlService.delete(id);
		if (result >= 1) {
			return "删除成功";
		} else {
			return "删除失败";
		}
	}

    @GetMapping(value = "/update", produces="text/html; charset=UTF-8")
	public String update(@Valid Girl girl) {
		int result = girlService.updateGirl(girl);
		if (result >= 1) {
			return "修改成功";
		} else {
			return "修改失败";
		}
 
	}
	
	@PostMapping(value = "/insert")
	public Girl insert(@RequestBody Girl girl) {
		return  girlService.insertGril(girl);
		
	}
	
	@RequestMapping("/listGirl")
	@ResponseBody
	public List<Girl> listGirl(){
		return girlService.listGirl();
	}
 
	@RequestMapping("/listGirlByAge")
	@ResponseBody
	public List<Girl> listGirlByAge(Integer age){
		return girlService.findByAge(age);
	}
}

