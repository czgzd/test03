package com.sinocarbon.integral.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinocarbon.integral.dto.UserAccountInfoDTO;
import com.sinocarbon.integral.service.UserAccountService;
import com.sinocarbon.polaris.commons.utils.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 积分管理 前端控制器
 * </p>
 *
 * @author 吴健
 * @since 2019-09-11
 */
@RestController
@RequestMapping("/api/integral")
@Api(value = "积分管理api")
public class UserAccountController {
	
	@Autowired
	private UserAccountService userAccountService;
	
	/**
	 * 
	 * @description 根据条件获取积分管理信息
	  * @param userName 员工
	  * @param tel 电话
	  * @param departmentName 部门
	 * @param jobName 职位
	 * @param minIntegral 最低积分
	 * @param maxIntegral 最高积分
	 * @param minExchangeIntegral 最低总豆
	 * @param maxExchangeIntegral 最高总豆
     * @param minCarbonSave 最低总豆
     * @param maxCarbonSave 最低总豆
	 * @return BaseResponse
	 * @author 吴健
	 * @date 2019/07/02
	 */
	@GetMapping(value = "/list/info")
	@ApiOperation(value="queryUserAccountList" , notes="查询积分管理列表")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "UserAccountInfoDTO", value = "积分管理实体类", dataType = "UserAccountInfoDTO", required = true),
		@ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页码", dataType = "Integer", required = false),
		@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", dataType = "Integer", required = false) })	
	public BaseResponse queryUserAccountList(UserAccountInfoDTO userAccountInfoDTO,Integer pageNum, Integer pageSize) {
		return userAccountService.listGoodsInfo(userAccountInfoDTO,pageNum,pageSize);
	}
	
	/**
	 * 
	 * @description 根据用户id获取用户信息
	 * @return BaseResponse
	 * @author 吴健
	 * @date 2019/09/17
	 */
	@GetMapping(value = "/user/{id}")
	@ApiOperation(value = "getOrderInfoById", notes = "根据id获取信息")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "path", name = "id", value = "用户ID", dataType = "int", required = true) })
	public BaseResponse getUserAccountInfoById(@PathVariable Integer id){
		return userAccountService.getUserAccountInfoById(id);
	}
	
}

