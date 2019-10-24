package com.sinocarbon.integral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinocarbon.integral.service.IntegralLogService;
import com.sinocarbon.polaris.commons.utils.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 积分日志表 前端控制器
 * </p>
 *
 * @author 葛明明123
 * @since 2019-06-26
 */
@RestController
@RequestMapping("/api/integral")
@Api(value = "个人积分日志api")
public class IntegralLogController {

	@Autowired
	private IntegralLogService integralLogService;


	/**
	 * 
	 * @description 查询个人积分日志记录
	 * @param trdSession api访问密钥
	 * @param pageNum    当前页码
	 * @param pageSize   每页条数
	 * @param serchType  0 代表一周 1 上周 2 三个月
	 * @throws Exception
	 * @return BaseResponse
	 * @author 江辉
	 * @date 2019/07/11
	 * @modifyDescription
	 * @modifier
	 * @modifyDate
	 */
	//@PreAuthorize("hasAuthority('integral-service:get/api/integral/log')")
	@GetMapping(value = "/integral/log")
	@ApiOperation(value = "getUserIntegralLog", notes = "查询个人积分日志记录")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "serchType", value = "1:本周 2:上周 3:三个月", dataType = "int", required = false),
			@ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", dataType = "int", required = false),
			@ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页条数", dataType = "int", required = false),
			@ApiImplicitParam(paramType = "query", name = "operationTypeId", value = "1:骑行 2:步行 ", dataType = "int", required = false),
})
	public BaseResponse getUserIntegralLog(Integer serchType, Integer pageNum, Integer pageSize,Integer operationTypeId) {
		return integralLogService.listUserIntegralLog(serchType, pageNum, pageSize,operationTypeId);
	}

	/**
	 * 
	 * @description 查询日记
	 * @param serchType 0 这周 1上周
	 * @throws Exception
	 * @return BaseResponse
	 * @author 江辉
	 * @date 2019/07/18
	 * @modifyDescription
	 * @modifier
	 * @modifyDate
	 */
	//@PreAuthorize("hasAuthority('integral-service:get/api/integral/week/diary')")
	@GetMapping(value = "/week/diary")
	@ApiOperation(value = "getWeekDiary", notes = "查询日记")
	@ApiImplicitParam(paramType = "query", name = "serchType", value = "1:本周 2:上周 ", dataType = "int", required = true)
	public BaseResponse getWeekDiary(Integer serchType ) {

		return integralLogService.listWeekDiary(serchType);
	}

}
