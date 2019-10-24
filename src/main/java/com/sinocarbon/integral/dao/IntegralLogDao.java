package com.sinocarbon.integral.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.sinocarbon.integral.pojo.IntegralLog;
import com.sinocarbon.integral.pojo.IntegralLogAndTypeNamePOJO;

/**
 * <p>
 * 积分日志表 Mapper 接口
 * </p>
 *
 * @author 葛明明123
 * @since 2019-06-26
 */
public interface IntegralLogDao extends BaseMapper<IntegralLog> {

	List<IntegralLogAndTypeNamePOJO> listIntegralLog(@Param("userId") Integer userId, @Param("serchType") Integer serchType,
			Pagination page, @Param("operationTypeId") Integer operationTypeId,@Param("registrationCode") String registrationCode);

	List<IntegralLogAndTypeNamePOJO> listIntegralLog(@Param("userId") Integer userId, @Param("serchType") Integer serchType,
			@Param("operationTypeId") Integer operationTypeId,@Param("registrationCode") String registrationCode);

	List<IntegralLogAndTypeNamePOJO> listIntegralLogGroup(@Param("userId") Integer userId,
			@Param("serchType") Integer serchType,@Param("registrationCode") String registrationCode);

	IntegralLog getToadyLastIntegralLog(IntegralLog integralLog);
	
	
	ArrayList<IntegralLogAndTypeNamePOJO> countLastWeekInfinityCard(@Param("userId") Integer userId,@Param("registrationCode") String registrationCode);
	
    @Select("select unionid from app_user_info where id = #{id}")
     Object  getAppUserInof(Integer id);

}
