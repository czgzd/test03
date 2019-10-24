package com.sinocarbon.integral.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sinocarbon.integral.constant.ErrorCode;
import com.sinocarbon.integral.constant.IntegralConstant;
import com.sinocarbon.integral.dao.IntegralDaySummaryDao;
import com.sinocarbon.integral.dao.UserAccountInfoDao;
import com.sinocarbon.integral.pojo.AppUserInfo;
import com.sinocarbon.integral.pojo.IntegralDaySummary;
import com.sinocarbon.integral.pojo.UserAccountInfo;
import com.sinocarbon.integral.pojo.UserAccountPOJO;
import com.sinocarbon.integral.pojo.UserIntegralDaySummaryPOJO;
import com.sinocarbon.integral.service.UserAccountInfoService;
import com.sinocarbon.integral.utils.DateUtils;
import com.sinocarbon.integral.utils.HeaderUtil;
import com.sinocarbon.integral.utils.IsNotEmptyUtil;
import com.sinocarbon.integral.utils.RedisUtil;
import com.sinocarbon.integral.utils.TransformationBeansUtil;
import com.sinocarbon.polaris.commons.pojo.LoginAppUser;
import com.sinocarbon.polaris.commons.utils.BaseResponse;
import com.sinocarbon.polaris.commons.utils.PublicCommonsUtils;

@Service
public class UserAccountInfoServiceImpl extends ServiceImpl<UserAccountInfoDao, UserAccountInfo>
		implements UserAccountInfoService {

	@Autowired
	RedisUtil redisUtil;
	@Autowired
	UserAccountInfoDao integralEmissionUserDao;
	
	@Autowired
	IntegralDaySummaryDao integralDaySummaryDao;
	


	@Override
	public BaseResponse getUserBeans() {

		UserAccountInfo integralEmissionUser = new UserAccountInfo();
		integralEmissionUser.setUserId( HeaderUtil.getWxUserId());

		UserAccountInfo integralEmissionUserNew = integralEmissionUserDao.getUserAccountInfo(integralEmissionUser);
		if (integralEmissionUserNew == null) {
			return BaseResponse.failed(ErrorCode.CODE_2003005, ErrorCode.CODE_2003005_MSG);

		}
		UserAccountPOJO userAccountPOJO=new UserAccountPOJO();
		TransformationBeansUtil.populate(integralEmissionUserNew, userAccountPOJO);
		return BaseResponse.successed(userAccountPOJO);
	}

	/**
	 * 
	 * @description 查询积分总排行榜
	 * @param trdSession 用户信息秘钥
	 * @param pageNum    当前页码
	 * @param pageSize   每页条数
	 * @throws Exception
	 * @return BaseResponse
	 * @author 江辉
	 * @date 2019/07/18
	 * @modifyDescription
	 * @modifier
	 * @modifyDate
	 */
	@Override
	public BaseResponse listGetUserIntegral(Integer pageNum, Integer pageSize) {

	
		if (pageNum == null || pageNum == 0) {
			pageNum = IntegralConstant.DEFAULT_PAGE_NUMBER;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = IntegralConstant.DEFAULT_PAGE_SIZE;
		}
		LoginAppUser loginAppUser=PublicCommonsUtils.getLoginAppUser();
		AppUserInfo appUserInfo=new AppUserInfo();
		appUserInfo.setRegistrationCode(loginAppUser.getTenantId());
		Pagination page = new Pagination(pageNum, pageSize);
		List<UserAccountPOJO> totalList = baseMapper.listUserAccountPOJO(appUserInfo, page);
		return BaseResponse.successed(totalList);
	}

	/**
	 * @description 更新用户积分相关信息
	 * @param dataMap 数据map
	 * @throws Exception
	 * @return BaseResponse
	 * @author wangxiaowei
	 * @date 2019/07/10
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResponse updateOrInsertUserIntegralAndBean(UserAccountInfo userAccountInfo) {

		long userId = userAccountInfo.getUserId();

		LocalDateTime updateTime = DateUtils.getLocalDate();

		List<UserAccountInfo> selectList = baseMapper
				.selectList(new EntityWrapper<UserAccountInfo>().eq("user_id", userId));
		if (!selectList.isEmpty()) { // 更新
			UserAccountInfo integralUserInfo = selectList.get(0);
			integralUserInfo.setEmissionReduction(userAccountInfo.getEmissionReduction() == null ? 0
					: userAccountInfo.getEmissionReduction() + integralUserInfo.getEmissionReduction());
			integralUserInfo.setBean(
					userAccountInfo.getBean() == null ? 0 : userAccountInfo.getBean() + integralUserInfo.getBean());
			integralUserInfo
					.setTotalBean(userAccountInfo.getTotalBean() == null || userAccountInfo.getTotalBean() < 0 ? 0
							: userAccountInfo.getTotalBean() + integralUserInfo.getTotalBean());
			integralUserInfo.setIntegral(userAccountInfo.getIntegral() == null ? 0
					: userAccountInfo.getIntegral() + integralUserInfo.getIntegral());
			integralUserInfo.setGmtModified(updateTime);
			if (userAccountInfo.getRegistrationCode() != null) {
				integralUserInfo.setRegistrationCode(userAccountInfo.getRegistrationCode());
			}
			
			//新增字段
			double bikeKilometre=0.0;
			Integer bikeIntegral=0;
			Integer walkStep=0;
			Integer walkIntegral=0;
			Integer onLineDays=0;
			Integer signIntegral=0;
			Integer signNumber=0;
			Integer loginIntegral=0;
			Integer exchangesNumber=0;
			
			if(integralUserInfo.getBikeKilometre()!=null) {
				bikeKilometre=integralUserInfo.getBikeKilometre();
			}
			if(integralUserInfo.getBikeIntegral()!=null) {
				bikeIntegral=integralUserInfo.getBikeIntegral();
			}
			if(integralUserInfo.getWalkStep()!=null) {
				walkStep=integralUserInfo.getWalkStep();
			}
			if(integralUserInfo.getWalkIntegral()!=null) {
				walkIntegral=integralUserInfo.getWalkIntegral();
			}
			if(integralUserInfo.getOnLineDays()!=null) {
				onLineDays=integralUserInfo.getOnLineDays();
			}
			if(integralUserInfo.getSignIntegral()!=null) {
				signIntegral=integralUserInfo.getSignIntegral();
			}
			if(integralUserInfo.getSignNumber()!=null) {
				signNumber=integralUserInfo.getSignNumber();
			}
			if(integralUserInfo.getLoginIntegral()!=null) {
				loginIntegral=integralUserInfo.getLoginIntegral();
			}
			if(integralUserInfo.getExchangesNumber()!=null) {
				exchangesNumber=integralUserInfo.getExchangesNumber();
			}
			integralUserInfo.setBikeKilometre(userAccountInfo.getBikeKilometre()+bikeKilometre);
			integralUserInfo.setBikeIntegral(userAccountInfo.getBikeIntegral()+bikeIntegral);
			integralUserInfo.setWalkStep(userAccountInfo.getWalkStep()+walkStep);
			integralUserInfo.setWalkIntegral(userAccountInfo.getWalkIntegral()+walkIntegral);
			integralUserInfo.setOnLineDays(userAccountInfo.getOnLineDays()+onLineDays);
			integralUserInfo.setSignIntegral(userAccountInfo.getSignIntegral()+signIntegral);
			integralUserInfo.setSignNumber(userAccountInfo.getSignNumber()+signNumber);
			integralUserInfo.setLoginIntegral(userAccountInfo.getLoginIntegral()+loginIntegral);
			integralUserInfo.setExchangesNumber(userAccountInfo.getExchangesNumber()+exchangesNumber);
			
			baseMapper.updateById(integralUserInfo);

		} else { // 新增
			if (!IsNotEmptyUtil.isNotEmptyBatch(userAccountInfo.getIntegral(),
					userAccountInfo.getRegistrationCode())) { // 判空

				return BaseResponse.failed(ErrorCode.CODE_2003001, ErrorCode.CODE_2003001_MSG);
			}
			userAccountInfo.setGmtCreate(updateTime);
			baseMapper.insert(userAccountInfo);

		}

		return BaseResponse.successed(IntegralConstant.SUCCESS);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResponse updateBean(Double bean) {
		
		if(bean==null) {
			return BaseResponse.failed(ErrorCode.CODE_2003001, ErrorCode.CODE_2003001_MSG);
		}
		
		if(bean<=0) {
			return BaseResponse.failed(ErrorCode.CODE_2003003, ErrorCode.CODE_2003003_MSG);
		}
		
		

		UserAccountInfo integralEmissionUser = new UserAccountInfo();
		integralEmissionUser.setUserId( HeaderUtil.getWxUserId());

		UserAccountInfo integralEmissionUserNew = integralEmissionUserDao.getUserAccountInfo(integralEmissionUser);
			
		if (integralEmissionUserNew == null) {
			return BaseResponse.failed(ErrorCode.CODE_2003007, ErrorCode.CODE_2003007_MSG);

		}
		Double lastBean=integralEmissionUserNew.getBean();
		if(lastBean<bean) {
			return BaseResponse.failed(ErrorCode.CODE_2003007, ErrorCode.CODE_2003007_MSG);
		}
		lastBean=lastBean-bean;
		UserAccountInfo userAccountInfo=new UserAccountInfo();
		
		userAccountInfo.setBean(lastBean);
		userAccountInfo.setUserId( HeaderUtil.getWxUserId());
		userAccountInfo.setId(integralEmissionUserNew.getId());
		userAccountInfo.setGmtModified(DateUtils.getLocalDate());
		int result=baseMapper.updateById(userAccountInfo);
		if(result==1) {//每日汇总表更新兑换次数+消耗的豆
			List<UserIntegralDaySummaryPOJO> integralDaySummaryList=integralDaySummaryDao.listIntegralDaySummary( HeaderUtil.getWxUserId());
			UserIntegralDaySummaryPOJO userIntegralDaySummaryPOJO=integralDaySummaryList.get(0);
			IntegralDaySummary integralDaySummary=new IntegralDaySummary();
			Double consumptionBean=userIntegralDaySummaryPOJO.getConsumptionBean();
			Integer exchangesNumber=userIntegralDaySummaryPOJO.getExchangesNumber();
			if(consumptionBean==null) {
				userIntegralDaySummaryPOJO.setConsumptionBean(bean);
			}else {userIntegralDaySummaryPOJO.setConsumptionBean(bean+consumptionBean);}
			if(exchangesNumber==null) {
				userIntegralDaySummaryPOJO.setExchangesNumber(1);
			}else {
				userIntegralDaySummaryPOJO.setExchangesNumber(1+exchangesNumber);
			}
			integralDaySummary.setId(userIntegralDaySummaryPOJO.getId());
			integralDaySummary.setConsumptionBean(userIntegralDaySummaryPOJO.getConsumptionBean());
			integralDaySummary.setExchangesNumber(userIntegralDaySummaryPOJO.getExchangesNumber());
			integralDaySummary.setUserId( HeaderUtil.getWxUserId());
			integralDaySummary.setGmtModified(DateUtils.getLocalDate());
			integralDaySummaryDao.updateById(integralDaySummary);
		}
		return BaseResponse.successed(lastBean);
	}

	/**
	 * 订单关闭 返还无极豆
	 */
	@Override
	public BaseResponse updateUserBean(Double bean, Integer userId) {
		
		if(!IsNotEmptyUtil.isNotEmptyBatch(bean,userId)) {
			return BaseResponse.failed(ErrorCode.CODE_2003001, ErrorCode.CODE_2003001_MSG);
		}
		
		
		UserAccountInfo integralEmissionUser = new UserAccountInfo();
		integralEmissionUser.setUserId(userId);

		UserAccountInfo integralEmissionUserNew = integralEmissionUserDao.getUserAccountInfo(integralEmissionUser);
		if (integralEmissionUserNew == null) {
			return BaseResponse.failed(ErrorCode.CODE_2003005, ErrorCode.CODE_2003005_MSG);

		}
		
		Double userBean = integralEmissionUserNew.getBean();
		integralEmissionUserNew.setBean(userBean+bean);
		integralEmissionUserNew.setGmtModified(DateUtils.getLocalDate());
		
		integralEmissionUserDao.updateById(integralEmissionUserNew);
		
		return BaseResponse.successed(IntegralConstant.SUCCESS);
	}

}
