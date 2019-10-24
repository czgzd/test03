package com.sinocarbon.integral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinocarbon.integral.constant.ErrorCode;
import com.sinocarbon.integral.constant.IntegralConstant;
import com.sinocarbon.integral.service.IntegralLogService;
import com.sinocarbon.integral.service.SettlementCenterService;
import com.sinocarbon.integral.utils.DateUtils;
import com.sinocarbon.integral.utils.HeaderUtil;
import com.sinocarbon.polaris.commons.pojo.LoginAppUser;
import com.sinocarbon.polaris.commons.utils.BaseResponse;
import com.sinocarbon.polaris.commons.utils.PublicCommonsUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/integral")
@Api(value = "签到打卡Api")
public class IntegralSignController {
	@Autowired
	SettlementCenterService settlementCenterService;

	@Autowired
	private IntegralLogService integralLogService;

	/**
	 * 
	 * @description 早起打卡
	 * @return BaseResponse
	 * @author gemingming
	 * @date 2019/10/21
	 * @modifyDescription  增加了时间限制
	 * @modifier 葛明明
	 * @modifyDate 2019/10/21
	 */
	// @PreAuthorize("hasAuthority('integral-service:post/api/integral/sign')")
	@PostMapping("/sign")
	@ApiOperation(value = "sign", notes = "早起签到打卡")
	public BaseResponse sign() {
		Integer localHour = DateUtils.getLocalHours(DateUtils.getLocalDate());
		if (localHour < 5 || localHour > 8) {

			return BaseResponse.failed(ErrorCode.CODE_2003010, ErrorCode.CODE_2003010_MSG);
		}
		LoginAppUser loginAppUser = PublicCommonsUtils.getLoginAppUser();

		Integer userId = HeaderUtil.getWxUserId();
		String registerCode = loginAppUser.getTenantId();
		return BaseResponse.successed(settlementCenterService.settlementIntegralDaySummary(userId, null, registerCode,
				IntegralConstant.OPERATION_SIGN, IntegralConstant.SIGN_INTEGRAL, 0.0, 0.0, 0));
	}

	/**
	 * 
	 * @description 查询今日是否已经签到
	 * @return BaseResponse
	 * @author 葛明明
	 * @date 2019/07/21
	 */
	// @PreAuthorize("hasAuthority('integral-service:get/api/integral/sign/status')")
	@GetMapping(value = "/sign/status")
	@ApiOperation(value = "getSignStatus", notes = "获取今日签到状况 0:未签到  1已签到")
	public BaseResponse getSignStatus() {

		return integralLogService.getSignStatus();
	}

}
