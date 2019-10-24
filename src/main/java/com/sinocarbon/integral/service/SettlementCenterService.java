package com.sinocarbon.integral.service;

import com.baomidou.mybatisplus.service.IService;
import com.sinocarbon.integral.pojo.IntegralLog;
import com.sinocarbon.polaris.commons.utils.BaseResponse;

public interface SettlementCenterService extends IService<IntegralLog> {

	BaseResponse settlementIntegralDaySummary( Integer userId,Integer taskCopyId,String registrationCode,Integer operationTypeId, Integer integral,
			Double emissionReduction, Double ridingKilometre, Integer step);

}
