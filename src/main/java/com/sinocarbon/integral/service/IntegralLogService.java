package com.sinocarbon.integral.service;


import com.baomidou.mybatisplus.service.IService;
import com.sinocarbon.integral.pojo.IntegralLog;
import com.sinocarbon.polaris.commons.utils.BaseResponse;

/**
 * <p>
 * 积分日志表 服务类
 * </p>
 *
 * @author 葛明明123
 * @since 2019-06-26
 */
public interface IntegralLogService extends IService<IntegralLog> {

	BaseResponse listUserIntegralLog(Integer serchType,Integer pageNum,Integer pageSize,Integer operationTypeId);
	BaseResponse listWeekDiary(Integer serchType );
	BaseResponse getSignStatus();
	BaseResponse getLoginStatus();


	
}
