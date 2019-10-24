package com.sinocarbon.integral.service.impl;



import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sinocarbon.integral.constant.ErrorCode;
import com.sinocarbon.integral.dao.UserAccountDao;
import com.sinocarbon.integral.dto.UserAccountInfoDTO;
import com.sinocarbon.integral.pojo.UserAccountInfoPOJO;
import com.sinocarbon.integral.pojo.UserAccountInputPOJO;
import com.sinocarbon.integral.service.UserAccountService;
import com.sinocarbon.integral.utils.TransformationBeansUtil;
import com.sinocarbon.integral.vo.UserAccountInfoVO;
import com.sinocarbon.polaris.commons.pojo.LoginAppUser;
import com.sinocarbon.polaris.commons.utils.BaseResponse;
import com.sinocarbon.polaris.commons.utils.PublicCommonsUtils;

@Service
public class UserAccountServicelmpl extends ServiceImpl<UserAccountDao, UserAccountInfoPOJO>
implements UserAccountService{

	@Override
	public BaseResponse listGoodsInfo(UserAccountInfoDTO userAccountInfoDTO, Integer pageNum, Integer pageSize) {
		LoginAppUser loginAppUser = PublicCommonsUtils.getLoginAppUser();
		String registrationCode = loginAppUser.getTenantId();
		UserAccountInputPOJO userAccountInputPOJO=new UserAccountInputPOJO();
		TransformationBeansUtil.populate(userAccountInfoDTO, userAccountInputPOJO);
		Pagination page = null;
		if (pageSize == null || pageSize == 0) {
			pageSize = 10;
		}
		if (pageNum == null || pageNum == 0) {
			pageNum = 1;
		}
		userAccountInputPOJO.setRegistrationCode(registrationCode);
		page = new Pagination(pageNum, pageSize);
		List<UserAccountInfoPOJO> listUserAccountInfo= baseMapper.listUserAccountInfo(userAccountInputPOJO,page);
		List<UserAccountInfoVO> userAccountInfoVOList = new ArrayList<>();
		TransformationBeansUtil.populateList(listUserAccountInfo, userAccountInfoVOList, UserAccountInfoVO.class);
		return BaseResponse.successedByPage(userAccountInfoVOList, page);
	}

	@Override
	public BaseResponse getUserAccountInfoById(Integer id) {
		 if(id==null) {
	    	  return  BaseResponse.failed(ErrorCode.CODE_2003001, ErrorCode.CODE_2003001_MSG);
	      }
		 UserAccountInputPOJO userAccountInputPOJO=new UserAccountInputPOJO();
		 userAccountInputPOJO.setId(id);
		 UserAccountInfoPOJO userAccountInfoPOJO=baseMapper.getUserAccountInfoById(userAccountInputPOJO);
		 UserAccountInfoVO userAccountInfoVO=new UserAccountInfoVO();
		 TransformationBeansUtil.populate(userAccountInfoPOJO, userAccountInfoVO);
		return BaseResponse.successed(userAccountInfoPOJO);
	}
	
}
