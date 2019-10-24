package com.sinocarbon.integral.service;

import com.baomidou.mybatisplus.service.IService;
import com.sinocarbon.integral.pojo.IntegralDaySummary;
import com.sinocarbon.polaris.commons.utils.BaseResponse;

/**
 * <p>
 * 用户积分减排表 服务类
 * </p>
 *
 * @author 葛明明123
 * @since 2019-06-26
 */
public interface IntegralDaySummaryService extends IService<IntegralDaySummary> {
	
	BaseResponse getUserIntegralEmission();

	BaseResponse listIntegralRank( Integer pageNum, Integer pageSize,Integer serchType);

	BaseResponse countEmissionSummary(Integer searchType);
	
	
}
