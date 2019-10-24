package com.sinocarbon.integral.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.sinocarbon.integral.pojo.UserAccountInfoPOJO;
import com.sinocarbon.integral.pojo.UserAccountInputPOJO;

/**
 * <p>
 * 积分管理 Mapper接口
 * </p>
 *
 * @author 吴健
 * @since 2019-09-10
 */
public interface UserAccountDao extends BaseMapper<UserAccountInfoPOJO> {

	List<UserAccountInfoPOJO> listUserAccountInfo(UserAccountInputPOJO orderSelectInfo,Pagination page);	
	
	UserAccountInfoPOJO   getUserAccountInfoById(UserAccountInputPOJO userAccountInputPOJO);
}
