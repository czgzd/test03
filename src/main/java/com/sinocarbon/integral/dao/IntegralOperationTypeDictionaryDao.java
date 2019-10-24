package com.sinocarbon.integral.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.sinocarbon.integral.pojo.IntegralOperationTypeDictionary;

/**
 * <p>
 * 积分获取途径类型表 Mapper 接口
 * </p>
 *
 * @author 葛明明123
 * @since 2019-06-26
 */
public interface IntegralOperationTypeDictionaryDao extends BaseMapper<IntegralOperationTypeDictionary> {

	IntegralOperationTypeDictionary getOperationTypeDictionary(int operationTypeId,String registrationCode);

}
