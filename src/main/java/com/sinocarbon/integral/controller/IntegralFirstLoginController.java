package com.sinocarbon.integral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinocarbon.integral.constant.IntegralConstant;
import com.sinocarbon.integral.service.IntegralLogService;
import com.sinocarbon.integral.service.SettlementCenterService;
import com.sinocarbon.integral.utils.HeaderUtil;
import com.sinocarbon.polaris.commons.pojo.LoginAppUser;
import com.sinocarbon.polaris.commons.utils.BaseResponse;
import com.sinocarbon.polaris.commons.utils.PublicCommonsUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/integral")
@Api(value = "每日首登赠送积分Api")
public class IntegralFirstLoginController {

	@Autowired
	SettlementCenterService settlementCenterService;

	@Autowired
	private IntegralLogService integralLogService;

	/**
	 * 
	 * @description 每日首次登录赠送积分
	 * @return BaseResponse
	 * @author gemingming
	 * @date 2019/07/21
	 */
	// @PreAuthorize("hasAuthority('integral-service:post/api/integral/login/integral')")
	@PostMapping("/login/integral")
	@ApiOperation(value = "sign", notes = "早起签到打卡")
	public BaseResponse sign() {

		LoginAppUser loginAppUser = PublicCommonsUtils.getLoginAppUser();

		// 获取userId
		Integer userId =  HeaderUtil.getWxUserId();
		String registerCode = loginAppUser.getTenantId();
		return BaseResponse.successed(settlementCenterService.settlementIntegralDaySummary(userId,null, registerCode,
				IntegralConstant.OPERATION_LOGIN, IntegralConstant.LOGIN_INTEGRAL, 0.0, 0.0, 0));
	}

	/**
	 * 
	 * @description 查询今日是否已经登录赠送积分
	 * @return BaseResponse
	 * @author 葛明明
	 * @date 2019/07/21
	 */
	// @PreAuthorize("hasAuthority('integral-service:get/api/integral/login/status')")
	@GetMapping(value = "/login/status")
	@ApiOperation(value = "getLoginStatus", notes = "查询今日是否已经登录赠送积分 0:未赠送  1已赠送")
	public BaseResponse getLoginStatus() {

		return integralLogService.getLoginStatus();
	}

}
