package com.sinocarbon.integral.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sinocarbon.integral.constant.ErrorCode;
import com.sinocarbon.integral.constant.IntegralConstant;
import com.sinocarbon.integral.dao.IntegralDaySummaryDao;
import com.sinocarbon.integral.dto.CountIntegralEmissionSummaryDTO;
import com.sinocarbon.integral.dto.UserDaySummaryDTO;
import com.sinocarbon.integral.pojo.AppUserInfo;
import com.sinocarbon.integral.pojo.IntegralDaySummary;
import com.sinocarbon.integral.pojo.UserIntegralDaySummaryPOJO;
import com.sinocarbon.integral.service.IntegralDaySummaryService;
import com.sinocarbon.integral.utils.HeaderUtil;
import com.sinocarbon.integral.utils.JackSonUtil;
import com.sinocarbon.integral.utils.RedisUtil;
import com.sinocarbon.integral.utils.TransformationBeansUtil;
import com.sinocarbon.polaris.commons.pojo.LoginAppUser;
import com.sinocarbon.polaris.commons.utils.BaseResponse;
import com.sinocarbon.polaris.commons.utils.PublicCommonsUtils;

/**
 * <p>
 * 用户积分减排表 服务实现类
 * </p>
 *
 * @author 葛明明123
 * @since 2019-06-26
 */
@Service
public class IntegralDaySummaryServiceImpl extends ServiceImpl<IntegralDaySummaryDao, IntegralDaySummary>
		implements IntegralDaySummaryService {

	@Autowired
	private RedisUtil redisTimeplateUtil;

	@Autowired
	private JackSonUtil jackSonUtil;
	

	/**
	 * 
	 * @description 查询每日积分，减排量以及中创豆
	 * @param trdSession 用户信息秘钥 获取用户实体类以及openId
	 * @throws Exception
	 * @return BaseResponse
	 * @author 江辉
	 * @date 2019/07/05
	 * @modifyDescription
	 * @modifyDate
	 */
	@Override
	public BaseResponse getUserIntegralEmission() {
	
		Integer userId =  HeaderUtil.getWxUserId();
		UserDaySummaryDTO integralUserDaySummaryDTO;
		integralUserDaySummaryDTO = redisTimeplateUtil.hgetObjByJson(IntegralConstant.USER_TODAY_INTEGRAL_EMISSION_MAP,
				userId + "", UserDaySummaryDTO.class);
		if (integralUserDaySummaryDTO == null) {

			UserIntegralDaySummaryPOJO userIntegralDaySummaryPOJO = baseMapper.getTodayIntegralDaySummary(userId);
			if (userIntegralDaySummaryPOJO == null) {
				return BaseResponse.failed(ErrorCode.CODE_2003005, ErrorCode.CODE_2003005_MSG);
			}
			integralUserDaySummaryDTO = new UserDaySummaryDTO();
			redisTimeplateUtil.hsetJson(IntegralConstant.USER_TODAY_INTEGRAL_EMISSION_MAP, userId + "",
					TransformationBeansUtil.populate(userIntegralDaySummaryPOJO, integralUserDaySummaryDTO));

		}
		return BaseResponse.successed(integralUserDaySummaryDTO);

	}

	/**
	 * 
	 * @description 查询用户积分并排序
	 * @param trdSession 用户信息秘钥 获取用户实体类以及openId
	 * @param pageNum    当前页码
	 * @param pageSize   每页条数
	 * @throws Exception
	 * @return BaseResponse
	 * @author 江辉
	 * @date 2019/07/05
	 * @modifyDescription
	 * @modifyDate
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public BaseResponse listIntegralRank(Integer pageNum, Integer pageSize, Integer serchType) {
	
		Map<Object, Object> userMap = redisTimeplateUtil.hmget(IntegralConstant.USER_INFO_MAP);
		if (userMap == null || userMap.size() == 0) {
			return BaseResponse.failed(ErrorCode.CODE_2003005, ErrorCode.CODE_2003005_MSG);
		}
		if(serchType==null) {
			serchType=0;
		}
		LoginAppUser loginAppUser=PublicCommonsUtils.getLoginAppUser();

		

		String registrationCode = loginAppUser.getTenantId();
		Map dataMap = (Map) userMap.get(registrationCode);
		if (serchType == 1) {
		return	this.getDefaultUserIntegralRankList(dataMap, registrationCode);
		}
		Pagination page = new Pagination(pageNum, pageSize);
		List<UserIntegralDaySummaryPOJO> userIntegralDaySummaryPOJOList = baseMapper
				.listTodayUserIntegralDaySummaryPOJO(registrationCode, page);
		if (userIntegralDaySummaryPOJOList.isEmpty()) {
			return BaseResponse.failed(ErrorCode.CODE_2003005, ErrorCode.CODE_2003005_MSG);
		}
		List<UserDaySummaryDTO> userIntegralRankList = new ArrayList<>();
		TransformationBeansUtil.populateList(userIntegralDaySummaryPOJOList, userIntegralRankList,
				UserDaySummaryDTO.class);
		return BaseResponse.successed(userIntegralRankList);
	}

	@SuppressWarnings("rawtypes")
	private BaseResponse getDefaultUserIntegralRankList(Map dataMap, String registrationCode) {
		List<UserDaySummaryDTO> userIntegralRankList = new ArrayList<>();
		Set<ZSetOperations.TypedTuple<Object>> userIntegralRankSet = redisTimeplateUtil
				.sortRevorseRang(IntegralConstant.USER_TODAY_INTEGRAL_RANK_PREFIX + registrationCode, 0, 0);
		if (null != userIntegralRankSet && !userIntegralRankSet.isEmpty()) {
			Iterator<TypedTuple<Object>> iterator = userIntegralRankSet.iterator();
			
			// redis不是空，则直接去redis中取数据
			while (iterator.hasNext()) {
				ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
				// 取到key-userid;value-积分
				Object key = typedTuple.getValue();
				Double value = typedTuple.getScore();								
				if(dataMap.get(Integer.valueOf(key.toString()))!=null){
					UserDaySummaryDTO userDaySummaryDTO;
					String jsonStr = dataMap.get(Integer.valueOf(key.toString())).toString();
					AppUserInfo appUserInfo = jackSonUtil.json2javaBean(jsonStr, AppUserInfo.class);
					userDaySummaryDTO = turnToUserInfoDTO(appUserInfo);
					userDaySummaryDTO.setIntegral(value.intValue());
					userIntegralRankList.add(userDaySummaryDTO);
				}
				

				
			}
		} else {
			// redis中为空的时候,从数据库每日积分减排中取并且排序如果pagesize
			Pagination page = new Pagination(IntegralConstant.DEFAULT_PAGE_NUMBER,
					IntegralConstant.DEFAULT_PAGE_SIZE_20);
			List<UserIntegralDaySummaryPOJO> userIntegralDaySummaryPOJOList = baseMapper
					.listTodayUserIntegralDaySummaryPOJO(registrationCode, page);
			if (userIntegralDaySummaryPOJOList == null || userIntegralDaySummaryPOJOList.isEmpty()) {
				return BaseResponse.failed(ErrorCode.CODE_2003005, ErrorCode.CODE_2003005_MSG);
			}
			TransformationBeansUtil.populateList(userIntegralDaySummaryPOJOList, userIntegralRankList,
					UserDaySummaryDTO.class);
			Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = new HashSet<>();
			ZSetOperations.TypedTuple<Object> typedTuple1;
			for (int i = 0; i < userIntegralDaySummaryPOJOList.size(); i++) {
				typedTuple1 = new DefaultTypedTuple<>(String.valueOf(userIntegralDaySummaryPOJOList.get(i).getUserId()),
						userIntegralDaySummaryPOJOList.get(i).getIntegral() + 0.0);
				typedTupleSet.add(typedTuple1);
			}
			redisTimeplateUtil.sortSetAddAll(IntegralConstant.USER_TODAY_INTEGRAL_RANK_PREFIX + registrationCode,
					typedTupleSet);
		}
		return BaseResponse.successed(userIntegralRankList);

	}

	/**
	 * 
	 * @description 将用户实体类转换成DTO类
	 * @param appUserInfo 用户信息实体类
	 * @throws Exception
	 * @return BaseResponse
	 * @author 江辉
	 * @date 2019/07/05
	 * @modifyDescription
	 * @modifyDate
	 */
	public UserDaySummaryDTO turnToUserInfoDTO(AppUserInfo appUserInfo) {
		UserDaySummaryDTO userInfoDTO = new UserDaySummaryDTO();
		userInfoDTO.setUserGender(appUserInfo.getUserGender());
		userInfoDTO.setHeadImg(appUserInfo.getUserImg());
		userInfoDTO.setUserNickName(appUserInfo.getUserNickname());
		userInfoDTO.setUserSignature(appUserInfo.getUserSignature());
		return userInfoDTO;
	}

	/**
	 * @description 积分/减排/中创豆汇总值
	 * @param searchType 0：本周数据 1:30天内数据
	 * @throws Exception
	 * @return BaseResponse
	 * @author 江辉
	 * @date 2019/07/26
	 * @modifyDescription
	 * @modifier
	 * @modifyDate
	 */
	@Override
	public BaseResponse countEmissionSummary(Integer searchType) {

		
	
		if(searchType==null) {
			searchType=0;
		}
		IntegralDaySummary integralDaySummary = null;
		CountIntegralEmissionSummaryDTO integralDayEmissionSummaryDTO = new CountIntegralEmissionSummaryDTO();

		Integer userId = HeaderUtil.getWxUserId();
		integralDaySummary = baseMapper.countIntegralDaySummary(userId, searchType);
		if(null == integralDaySummary) {
			return BaseResponse.failed(ErrorCode.CODE_2003005, ErrorCode.CODE_2003005_MSG);
		}
		TransformationBeansUtil.populate(integralDaySummary, integralDayEmissionSummaryDTO);
		return BaseResponse.successed(integralDayEmissionSummaryDTO);
	}

	

	

	

}
