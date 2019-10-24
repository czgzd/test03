package com.sinocarbon.integral.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.sinocarbon.integral.pojo.IntegralDaySummary;
import com.sinocarbon.integral.pojo.UserIntegralDaySummaryPOJO;
import com.sinocarbon.integral.pojo.UserSurplusBeanPOJO;

/**
 * <p>
 * 用户每日无极豆-减排-积分纪录 Mapper 接口
 * </p>
 *
 * @author 葛明明123
 * @since 2019-06-26
 */
public interface IntegralDaySummaryDao extends BaseMapper<IntegralDaySummary> {

	UserIntegralDaySummaryPOJO getTodayIntegralDaySummary(Integer userId);
	List<UserIntegralDaySummaryPOJO> listIntegralDaySummary(Integer userId);

	List<UserIntegralDaySummaryPOJO> listTodayUserIntegralDaySummaryPOJO(String registrationCode, Pagination page);
	
	IntegralDaySummary countIntegralDaySummary(Integer userId, Integer searchType);

	@SuppressWarnings("rawtypes")
	List<Map> countSomedayUserIntegral(@Param("dateStr") String dateStr);

	List<IntegralDaySummary> listAllRecordByDateStr(String dateStr);
	
	List<UserSurplusBeanPOJO> listBeanData();

}
