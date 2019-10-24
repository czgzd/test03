package com.sinocarbon.integral.service;


import com.baomidou.mybatisplus.service.IService;
import com.sinocarbon.integral.dto.UserAccountInfoDTO;
import com.sinocarbon.integral.pojo.UserAccountInfoPOJO;
import com.sinocarbon.polaris.commons.utils.BaseResponse;
/**
 * <p>
 * 积分管理 服务类
 * </p>
 *
 * @author 吴健
 * @since 2019-09-10
 */
public interface UserAccountService  extends IService<UserAccountInfoPOJO> {
	
	 BaseResponse listGoodsInfo(UserAccountInfoDTO userAccountInfoDTO,Integer pageNum, Integer pageSize);
	 BaseResponse getUserAccountInfoById(Integer id);
}
