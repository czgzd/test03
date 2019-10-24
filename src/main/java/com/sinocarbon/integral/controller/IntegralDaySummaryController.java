package com.sinocarbon.integral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinocarbon.integral.service.IntegralDaySummaryService;
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
public class IntegralDaySummaryController {

	@Autowired
	private IntegralDaySummaryService integralDaySummaryService;


	/**
	 * 
	 * @description 个人每日积分，减排量以及中创豆查询
	 * @return BaseResponse
	 * @author 江辉
	 * @date 2019/07/05
	 * @modifyDescription
	 * @modifyDate
	 */
	//@PreAuthorize("hasAuthority('integral-service:get/api/integral/day/summary')")
	@GetMapping("/day/summary")
	@ApiOperation(value = "getUserIntegralEmissionBean", notes = "个人每日积分，减排量以及中创豆查询")
	public BaseResponse getUserIntegralEmissionBean() {
		return integralDaySummaryService.getUserIntegralEmission();
	}
	
	/**
	 * 
	 * @description 个人每日积分，减排量以及中创豆查询（内部api）
	 * @return BaseResponse
	 * @author 吴健
	 * @date 2019/10/17
	 * @modifyDescription
	 * @modifyDate
	 */
	//@PreAuthorize("hasAuthority('integral-service:get/api/integral/day/summary')")
	@GetMapping("/day/bean")
	@ApiOperation(value = "getUserIntegral", notes = "个人每日积分，减排量以及中创豆查询")
	public BaseResponse getUserIntegral() {
		return integralDaySummaryService.getUserIntegralEmission();
	}


	/**
	 * 
	 * @description 每日积分排行榜查询
	 * @param trdSession 用户信息秘钥 获取用户实体类以及openId
	 * @param pageNum    当前页码
	 * @param pageSize   每页条数
	 * @param serchType  搜索类型
	 * @throws Exception
	 * @return BaseResponse
	 * @author 江辉
	 * @date 2019/07/05
	 * @modifyDescription
	 * @modifyDate
	 */
	//@PreAuthorize("hasAuthority('integral-service:get/api/integral/rank/integral')")
	@GetMapping("/rank/integral")
	@ApiOperation(value = "getIntegralRank", notes = "每日积分排行榜")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页码", dataType = "int", required = false),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", dataType = "int", required = false),
			@ApiImplicitParam(paramType = "query", name = "serchType", value = "1:获取系统默认指定值", dataType = "int",required = false)})
	public BaseResponse getIntegralRank(Integer pageNum, Integer pageSize,Integer serchType) {
		return integralDaySummaryService.listIntegralRank( pageNum, pageSize,serchType);
	}
	
	
	/**
	 * 
	 * @description 个人减排量/积分/中创豆汇总查询
	 * @param searchType  1:本周数据 2:30天内数据
	 * @throws Exception
	 * @return BaseResponse
	 * @author 江辉
	 * @date 2019/07/26
	 * @modifyDescription
	 * @modifier
	 * @modifyDate
	 */
	//@PreAuthorize("hasAuthority('integral-service:get/api/integral/count/emission')")
	@GetMapping(value = "/count/emission")
	@ApiOperation(value = "countEmission", notes = "个人减排量/积分/中创豆汇总查询")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "searchType", value = "1:本周数据 2:30天内数据", dataType = "int", required = false)})
	public BaseResponse countEmission(Integer searchType) {
		return integralDaySummaryService.countEmissionSummary(searchType);
	}
	

	
	

}
