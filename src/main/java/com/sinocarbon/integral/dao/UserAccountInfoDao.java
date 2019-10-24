package com.sinocarbon.integral.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.sinocarbon.integral.pojo.AppUserInfo;
import com.sinocarbon.integral.pojo.UserAccountInfo;
import com.sinocarbon.integral.pojo.UserAccountPOJO;

/**
 * <p>
 * 用户积分减排表 Mapper 接口
 * </p>
 *
 * @author 葛明明123
 * @since 2019-06-26
 */
public interface UserAccountInfoDao extends BaseMapper<UserAccountInfo> {
	
	UserAccountInfo getUserAccountInfo(UserAccountInfo userAccountInfo);
	
	List<UserAccountPOJO> listUserAccountPOJO(AppUserInfo appUserInfo, Pagination page);

	
	
}
