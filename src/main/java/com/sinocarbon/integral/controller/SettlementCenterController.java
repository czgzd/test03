package com.sinocarbon.integral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinocarbon.integral.dao.SettlementDTO;
import com.sinocarbon.integral.service.SettlementCenterService;
import com.sinocarbon.polaris.commons.utils.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 用户积分减排表 前端控制器
 * </p>
 *
 * @author 葛明明123
 * @since 2019-06-26
 */
@RestController
@RequestMapping("/api/integral")
@Api(value = "用户积分减排量无极豆Api")
public class SettlementCenterController {

	@Autowired
	private SettlementCenterService settlementCenterService;

	/**
	 * 
	 * @description 用户每日积分、减排量汇总
	 * @param trdSession                api访问密钥
	 * @param operationType             操作类型
	 * @param emissionReduction         用户产生的减排量
	 * @param emissionReductionIntegral 用户产生的积分
	 * @throws Exception
	 * @return BaseResponse
	 * @author 葛明明
	 * @date 2019/08/07
	 * @modifyDescription
	 * @modifier
	 * @modifyDate
	 */
//	// @PreAuthorize("hasAuthority('integral-service:post/api/integral/day/settlement')")
//	@PostMapping("/day/settlement")
//	@ApiOperation(value = "daySettlement", notes = "每日结算")
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "operationType", value = "操作类型1 步行 2 骑行", dataType = "int", required = true),
//			@ApiImplicitParam(paramType = "query", name = "emissionReduction", value = "用户产生的减排量", dataType = "doule", required = false),
//			@ApiImplicitParam(paramType = "query", name = "integral", value = "用户产生的积分", dataType = "int", required = true),
//			@ApiImplicitParam(paramType = "query", name = "ridingKilometre", value = "骑行公里数", dataType = "Double", required = true),
//			@ApiImplicitParam(paramType = "query", name = "step", value = "步数", dataType = "int", required = true),
//
//	})
//	public BaseResponse daySettlement(Integer operationType, Double emissionReduction, Integer integral,
//			Double ridingKilometre, Integer step) {
//		LoginAppUser loginAppUser = PublicCommonsUtils.getLoginAppUser();
//
//		// 获取userId
//		Integer userId =  HeaderUtil.getWxUserId();
//		String registerCode = loginAppUser.getTenantId();
//		return settlementCenterService.settlementIntegralDaySummary(userId,registerCode,operationType, integral, emissionReduction,
//				ridingKilometre, step);
//	}

	/**
	 * 
	 * @description 用户每日积分、减排量汇总
	 * @param trdSession                api访问密钥
	 * @param operationType             操作类型
	 * @param emissionReduction         用户产生的减排量
	 * @param emissionReductionIntegral 用户产生的积分
	 * @throws Exception
	 * @return BaseResponse
	 * @author 葛明明
	 * @date 2019/08/07
	 * @modifyDescription
	 * @modifier
	 * @modifyDate
	 */
	@PostMapping("/inside/day/settlement")
	@ApiOperation(value = "insideDaySettlement", notes = "每日结算(内部api)")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "query", name = "settlementDTO", value = "结算实体类", dataType = "SettlementDTO", required = true) })

	public BaseResponse insideDaySettlement(@RequestBody SettlementDTO settlementDTO) {

		return settlementCenterService.settlementIntegralDaySummary(settlementDTO.getUserId(),settlementDTO.getTaskCopyId(),
				settlementDTO.getRegisterCode(), settlementDTO.getOperationType(), settlementDTO.getIntegral(),
				settlementDTO.getEmissionReduction(), settlementDTO.getRidingKilometre(), settlementDTO.getStep());
	}

}
