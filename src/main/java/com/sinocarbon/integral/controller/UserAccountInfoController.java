package com.sinocarbon.integral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinocarbon.integral.service.UserAccountInfoService;
import com.sinocarbon.polaris.commons.utils.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/integral")
@Api(value = "获取用户总中创豆api")
public class UserAccountInfoController {

	@Autowired
	UserAccountInfoService userAccountInfoService;

	@GetMapping(value = "/user/beans")
	@ApiOperation(value = "getUserBeans", notes = "获取用户总中创豆api")
	public BaseResponse getUserBeans() {
		return userAccountInfoService.getUserBeans();
	}

	/**
	 * 
	 * @description 扣除bean
	 * @param bean 扣除的豆
	 * @author gemingming
	 * @date 2019/08/20
	 */
	//@PreAuthorize("hasAuthority('integral-service:put/api/integral/user/beans')")
	@PutMapping(value = "/user/beans")
	@ApiOperation(value = "getIntegralTotalList", notes = "购物扣豆API")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bean", value = "需扣除的豆", dataType = "double", required = true),
			})
	public BaseResponse deductionBean(Double bean) {

		return userAccountInfoService.updateBean(bean);

	}

	/**
	 * 
	 * @description 查询积分总排行榜
	 * @param pageNum  当前页码
	 * @param pageSize 每页条数
	 * @throws Exception
	 * @return BaseResponse
	 * @author 江辉
	 * @date 2019/07/18
	 * @modifyDescription
	 * @modifier
	 * @modifyDate
	 */
	//@PreAuthorize("hasAuthority('integral-service:get/api/integral/total/list')")
	@GetMapping(value = "/total/list")
	@ApiOperation(value = "getIntegralTotalList", notes = "查询积分总排行榜")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页码", dataType = "int", required = false),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", dataType = "int", required = false) })
	public BaseResponse getIntegralTotalList(Integer pageNum, Integer pageSize) {

		return userAccountInfoService.listGetUserIntegral(pageNum, pageSize);
	}
	
	
	
	
	/**
	 * 
	 * @description 订单关闭返还无极豆(内部API)
	 * @param bean 无极豆
	 * @author gemingming
	 * @date 2019/08/20
	 */
	//@PreAuthorize("hasAuthority('integral-service:put/api/integral/user/beans')")
	@PutMapping(value = "/user/back/beans/{userId}/{bean}")
	@ApiOperation(value = "updateuserBean", notes = "返还无极豆API")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "bean", value = "无极豆数量", dataType = "double", required = true),
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户Id", dataType = "int", required = true)

			})
	public BaseResponse updateuserBean(@PathVariable(value = "bean" ) Double bean,@PathVariable("userId") Integer userId) {

		return	userAccountInfoService.updateUserBean(bean, userId);
		
		 

	}
	
	

}
