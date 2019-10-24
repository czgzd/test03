package com.sinocarbon.integral.service;

import com.sinocarbon.integral.pojo.UserAccountInfo;
import com.sinocarbon.polaris.commons.utils.BaseResponse;

public interface UserAccountInfoService {
	
	BaseResponse getUserBeans();
	
	BaseResponse listGetUserIntegral( Integer pageNum, Integer pageSize);
	
	
	BaseResponse updateOrInsertUserIntegralAndBean(UserAccountInfo entity);
	
	BaseResponse updateBean(Double bean);
	
	 
	BaseResponse updateUserBean(Double bean,Integer userId);
	

}
