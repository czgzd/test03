//package com.sinocarbon.integral.service.impl;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.ZSetOperations;
//import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.baomidou.mybatisplus.service.impl.ServiceImpl;
//import com.sinocarbon.integral.constant.ErrorCode;
//import com.sinocarbon.integral.constant.IntegralConstant;
//import com.sinocarbon.integral.dao.IntegralDaySummaryDao;
//import com.sinocarbon.integral.dao.IntegralEmissionReductionLogDao;
//import com.sinocarbon.integral.dao.IntegralLogDao;
//import com.sinocarbon.integral.dao.IntegralOperationTypeDictionaryDao;
//import com.sinocarbon.integral.dto.IntegralLogAndTypeNameDTO;
//import com.sinocarbon.integral.dto.UserDaySummaryDTO;
//import com.sinocarbon.integral.pojo.IntegralDaySummary;
//import com.sinocarbon.integral.pojo.IntegralEmissionReductionLog;
//import com.sinocarbon.integral.pojo.IntegralLog;
//import com.sinocarbon.integral.pojo.IntegralOperationTypeDictionary;
//import com.sinocarbon.integral.pojo.UserIntegralDaySummaryPOJO;
//import com.sinocarbon.integral.service.SettlementCenterService;
//import com.sinocarbon.integral.utils.DateUtils;
//import com.sinocarbon.integral.utils.IsNotEmptyUtil;
//import com.sinocarbon.integral.utils.RedisUtil;
//import com.sinocarbon.integral.utils.TransformationBeansUtil;
//import com.sinocarbon.polaris.commons.utils.BaseResponse;
//
//@Service
//public class SettlementCenterServiceImpl extends ServiceImpl<IntegralLogDao, IntegralLog>
//		implements SettlementCenterService {
//
//	@Autowired
//	private RedisUtil redisTimeplateUtil;
//
//	@Autowired
//	private IntegralDaySummaryDao goUserEmissionIntegralBeanDao;
//
//	@Autowired
//	private IntegralOperationTypeDictionaryDao goOperationTypeDictionaryDao;
//
//	@Autowired
//	IntegralEmissionReductionLogDao goEmissionReductionLogDao;
//
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public BaseResponse settlementIntegralDaySummary(Integer userId,Integer taskCopyId, String registrationCode, Integer operationType,
//			Integer integral, Double emissionReduction, Double ridingKilometre, Integer step) {
//
//		if (!IsNotEmptyUtil.isNotEmptyBatch(operationType, integral)) {
//			return BaseResponse.failed(ErrorCode.CODE_2003001, ErrorCode.CODE_2003001_MSG);
//		}
//		if (operationType == IntegralConstant.OPERATION_SIGN || operationType == IntegralConstant.OPERATION_LOGIN) {
//			IntegralLog integralLog = new IntegralLog();
//			integralLog.setUserId(userId);
//			integralLog.setOperationTypeId(operationType);
//			integralLog = baseMapper.getToadyLastIntegralLog(integralLog);
//			if (integralLog != null) {
//				return BaseResponse.failed(ErrorCode.CODE_2003008, ErrorCode.CODE_2003008_MSG);
//			}
//		}
//
//		this.insertIntegralLog(userId, registrationCode, operationType, integral,taskCopyId);
//		this.insertOrUpdateSummary(userId, registrationCode, operationType, emissionReduction, integral,
//				ridingKilometre, step);
//		if (operationType == IntegralConstant.OPERATION_WALK || operationType == IntegralConstant.OPERATION_RIDING) {
//			this.insertEmissionLog(userId, registrationCode, operationType, emissionReduction);
//
//		}
//		return BaseResponse.successed(integral);
//	}
//
//	private void insertIntegralLog(Integer userId, String registrationCode, Integer operationTypeId, Integer integral,Integer taskCopyId) {
//			
//		IntegralLog integralLog = new IntegralLog();
//		if(taskCopyId!=null) {
//			integralLog.setTaskCopyId(taskCopyId);
//		}
//		integralLog.setUserId(userId);
//		integralLog.setRegistrationCode(registrationCode);
//		LocalDateTime logTime = DateUtils.getLocalDate();
//		integralLog.setIntegral(integral);
//		integralLog.setOperationTypeId(operationTypeId);
//		integralLog.setGmtCreate(logTime);
//		Integer result = baseMapper.insert(integralLog);
//		IntegralOperationTypeDictionary goOperationTypeDictionary = goOperationTypeDictionaryDao
//				.getOperationTypeDictionary(operationTypeId,registrationCode);
//		IntegralLogAndTypeNameDTO integralLogListDtO = new IntegralLogAndTypeNameDTO();
//		integralLogListDtO.setIntegral(integral);
//		integralLogListDtO.setGmtCreate(logTime);
//		integralLogListDtO.setOperationPictureUrl(goOperationTypeDictionary.getOperationPictureUrl());
//		integralLogListDtO.setOperationName(goOperationTypeDictionary.getOperationName());
//		if (result != 0) {
//			List<IntegralLogAndTypeNameDTO> integralLogListDTOList = redisTimeplateUtil
//					.hgetListByJson(IntegralConstant.USER_WEEK_DIARY_MAP, userId + "", IntegralLogAndTypeNameDTO.class);
//			if (integralLogListDTOList == null) {
//				integralLogListDTOList = new ArrayList<>();
//			}
//			integralLogListDTOList.add(0, integralLogListDtO);
//
//			redisTimeplateUtil.hsetJson(IntegralConstant.USER_WEEK_DIARY_MAP, userId + "", integralLogListDTOList);
//		}
//
//	}
//
//	private void insertEmissionLog(Integer userId, String registrationCode, Integer operationTypeId,
//			Double emissionReduction) {
//
//		IntegralEmissionReductionLog goEmissionReductionLog = new IntegralEmissionReductionLog();
//		goEmissionReductionLog.setEmissionReduction(emissionReduction);
//		goEmissionReductionLog.setOperationTypeId(operationTypeId);
//		goEmissionReductionLog.setUserId(userId);
//		goEmissionReductionLog.setRegistrationCode(registrationCode);
//		LocalDateTime logTime = DateUtils.getLocalDate();
//		goEmissionReductionLog.setGmtCreate(logTime);
//		goEmissionReductionLogDao.insert(goEmissionReductionLog);
//	}
//
//	private void insertOrUpdateSummary(Integer userId, String registrationCode, Integer operationType,
//			Double emissionReduction, Integer integral, Double ridingKilometre, Integer step) {
//		if (emissionReduction == null) {
//			emissionReduction = 0.0;
//		}
//		if (ridingKilometre == null) {
//			ridingKilometre = 0.0;
//		}
//		if (step == null) {
//			step = 0;
//		}
//
//		if (operationType == IntegralConstant.OPERATION_LOGIN) {
//			insertIntegralDaySummary(userId, registrationCode, integral);
//
//		} else {
//			UserIntegralDaySummaryPOJO userIntegralDaySummaryPOJO = goUserEmissionIntegralBeanDao
//					.getTodayIntegralDaySummary(userId);
//			if (userIntegralDaySummaryPOJO != null) {
//				updateIntegralDaySummary(userIntegralDaySummaryPOJO, userId, registrationCode, operationType,
//						emissionReduction, integral, ridingKilometre, step);
//			}
//
//		}
//
//	}
//
//	private void insertIntegralDaySummary(Integer userId, String registrationCode, Integer integral) {
//
//		IntegralDaySummary integralDaySummary = new IntegralDaySummary();
//
//		integralDaySummary.setLoginIntegral(integral);
//		integralDaySummary.setIntegral(integral);
//		integralDaySummary.setUserId(userId);
//		integralDaySummary.setGmtCreate(DateUtils.getLocalDate());
//		integralDaySummary.setRegistrationCode(registrationCode);
//		int result = goUserEmissionIntegralBeanDao.insert(integralDaySummary);
//		if (result != 0) {
//			UserDaySummaryDTO userIntegralDaySummaryDTO = new UserDaySummaryDTO();
//			TransformationBeansUtil.populate(integralDaySummary, userIntegralDaySummaryDTO);
//			redisTimeplateUtil.hsetJson(IntegralConstant.USER_TODAY_INTEGRAL_EMISSION_MAP, userId + "",
//					userIntegralDaySummaryDTO);
//			// 今日排行榜redis
//			updateRedisTheList(integral, userId, registrationCode);
//		}
//
//	}
//
//	private void updateIntegralDaySummary(UserIntegralDaySummaryPOJO userIntegralDaySummaryPOJO, Integer userId,
//			String registrationCode, Integer operationType, Double emissionReduction, Integer integral,
//			Double ridingKilometre, Integer step) {
//		IntegralDaySummary integralDaySummary = new IntegralDaySummary();
//
//		Double lastEmissionReduction = userIntegralDaySummaryPOJO.getEmissionReduction();
//		if (lastEmissionReduction == null) {
//			lastEmissionReduction = 0.0;
//		}
//
//		if (operationType == IntegralConstant.OPERATION_WALK) {
//			bindWalkParamsToBean(userIntegralDaySummaryPOJO, emissionReduction, integral, step);
//		} else if (operationType == IntegralConstant.OPERATION_RIDING) {
//			bindBikeParamsToBean(userIntegralDaySummaryPOJO, emissionReduction, ridingKilometre, integral);
//
//		} else if (operationType == IntegralConstant.OPERATION_SIGN) {
//			userIntegralDaySummaryPOJO.setSignIntegral(integral);
//		} else if (operationType == IntegralConstant.OPERATION_LOGIN) {
//			userIntegralDaySummaryPOJO.setLoginIntegral(integral);
//		}else if(operationType == IntegralConstant.OPERATION_TASK_FAIL) {
//			bindTaskParamsToBean(userIntegralDaySummaryPOJO,integral);
//		}
//		Double userEmissionReduction = lastEmissionReduction + emissionReduction;
//		Integer integralNew = userIntegralDaySummaryPOJO.getIntegral() + integral;
//		userIntegralDaySummaryPOJO.setEmissionReduction(userEmissionReduction);
//		userIntegralDaySummaryPOJO.setIntegral(integralNew);
//		TransformationBeansUtil.populate(userIntegralDaySummaryPOJO, integralDaySummary);
//
//		integralDaySummary.setGmtModified(DateUtils.getLocalDate());
//		int result = goUserEmissionIntegralBeanDao.updateById(integralDaySummary);
//
//		if (result != 0) {
//			UserDaySummaryDTO userIntegralDaySummaryDTO = new UserDaySummaryDTO();
//			TransformationBeansUtil.populate(userIntegralDaySummaryPOJO, userIntegralDaySummaryDTO);
//			redisTimeplateUtil.hsetJson(IntegralConstant.USER_TODAY_INTEGRAL_EMISSION_MAP, userId + "",
//					userIntegralDaySummaryDTO);
//			// 今日排行榜redis
//			updateRedisTheList(integral, userId, registrationCode);
//		}
//
//	}
//	
//	private UserIntegralDaySummaryPOJO bindTaskParamsToBean(UserIntegralDaySummaryPOJO userIntegralDaySummaryPOJO,
//			 Integer integral) {
//		Integer lastTaskIntegral = userIntegralDaySummaryPOJO.getChallengeIntegral();
//		
//		if (lastTaskIntegral == null) {
//			lastTaskIntegral = 0;
//		}
//		Integer taskIntegral = lastTaskIntegral + integral;
//		userIntegralDaySummaryPOJO.setChallengeIntegral(taskIntegral);
//		return userIntegralDaySummaryPOJO;
//	}
//
//	private UserIntegralDaySummaryPOJO bindWalkParamsToBean(UserIntegralDaySummaryPOJO userIntegralDaySummaryPOJO,
//			Double emissionReduction, Integer integral, Integer step) {
//
//		Double lastStepEmissionReduction = userIntegralDaySummaryPOJO.getWalkEmissionReduction();
//		Integer lastSetpIntegral = userIntegralDaySummaryPOJO.getWalkIntegral();
//		Integer lastStep = userIntegralDaySummaryPOJO.getWalkStep();
//
//		if (lastStepEmissionReduction == null) {
//			lastStepEmissionReduction = 0.0;
//		}
//		if (lastSetpIntegral == null) {
//			lastSetpIntegral = 0;
//		}
//		if (lastStep == null) {
//			lastStep = 0;
//		}
//
//		Double stepEmissionReduction = lastStepEmissionReduction + emissionReduction;
//		Integer walkIntegral = lastSetpIntegral + integral;
//		Integer walkStep = lastStep + step;
//		userIntegralDaySummaryPOJO.setWalkEmissionReduction(stepEmissionReduction);
//		userIntegralDaySummaryPOJO.setWalkIntegral(walkIntegral);
//		userIntegralDaySummaryPOJO.setWalkStep(walkStep);
//
//		return userIntegralDaySummaryPOJO;
//	}
//
//	private UserIntegralDaySummaryPOJO bindBikeParamsToBean(UserIntegralDaySummaryPOJO userIntegralDaySummaryPOJO,
//			Double emissionReduction, Double ridingKilometre, Integer integral) {
//		Double lastBikeEmissionReduction = userIntegralDaySummaryPOJO.getBikeEmissionReduction();
//		Double lastBikeKilometer = userIntegralDaySummaryPOJO.getBikeKilometre();
//		Integer lastBikeIntegral = userIntegralDaySummaryPOJO.getBikeIntegral();
//
//		if (lastBikeEmissionReduction == null) {
//			lastBikeEmissionReduction = 0.0;
//
//		}
//
//		if (lastBikeKilometer == null) {
//			lastBikeKilometer = 0.0;
//
//		}
//
//		if (lastBikeIntegral == null) {
//			lastBikeIntegral = 0;
//		}
//		Double bikeEmissionReduction = lastBikeEmissionReduction + emissionReduction;
//		Double bikeKilometre = lastBikeKilometer + ridingKilometre;
//		Integer bikeIntegral = lastBikeIntegral + integral;
//
//		userIntegralDaySummaryPOJO.setBikeEmissionReduction(bikeEmissionReduction);
//		userIntegralDaySummaryPOJO.setBikeIntegral(bikeIntegral);
//		userIntegralDaySummaryPOJO.setBikeKilometre(bikeKilometre);
//		return userIntegralDaySummaryPOJO;
//	}
//
//	/**
//	 * 
//	 * @description 更新redis中积分排行榜
//	 * @param appUserInfo 用户信息实体类
//	 * @throws Exception
//	 * @return BaseResponse
//	 * @author 江辉
//	 * @date 2019/07/05
//	 * @modifyDescription
//	 * @modifyDate
//	 */
//	@SuppressWarnings("unchecked")
//	private void updateRedisTheList(Integer integral, Integer userId, String registrationCode) {
//
//
//		// 从redis取出用户当天积分减排数据
//		UserDaySummaryDTO userIntegralDaySummaryDTO = redisTimeplateUtil
//				.hgetObjByJson(IntegralConstant.USER_TODAY_INTEGRAL_EMISSION_MAP, userId + "", UserDaySummaryDTO.class);
//		
//		Integer todayIntegral=userIntegralDaySummaryDTO.getIntegral();
//		Set<ZSetOperations.TypedTuple<Object>> userIntegralRankSet = redisTimeplateUtil
//				.sortRevorseRang(IntegralConstant.USER_TODAY_INTEGRAL_RANK_PREFIX + registrationCode, 0, 0);
//			
//		if (userIntegralRankSet != null && !userIntegralRankSet.isEmpty()) {// 有数据
//			Iterator<TypedTuple<Object>> iterator = userIntegralRankSet.iterator();
//			//遍历看自己是否已经在redis,在里面就更新
//			while (iterator.hasNext()) {
//				ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
//				// 取到key-userid;value-积分
//				Object mykey = typedTuple.getValue();
//				if (Integer.valueOf(mykey.toString()).equals(userId)) {
//					redisTimeplateUtil.sortSetIncrScore(
//							IntegralConstant.USER_TODAY_INTEGRAL_RANK_PREFIX + registrationCode, mykey + "",
//							integral);
//					return;
//				}
//			}
//			//遍历发现自己不在redis中,需要判断是否数据已经超过20条,小于20直接存
//			if(userIntegralRankSet.size()<20) {
//				// 如果不在里面直接添加一条数据即可
//				insertToRedisIntegralRank(registrationCode, todayIntegral, userId);
//				return;
//			}
//					
//			ZSetOperations.TypedTuple<Object> typedTuple = (TypedTuple<Object>) userIntegralRankSet.toArray()[19];
//			if (todayIntegral > Integer.valueOf(typedTuple.getValue().toString())) {
//				redisTimeplateUtil.sortSetRemoveValueByKeys(
//						IntegralConstant.USER_TODAY_INTEGRAL_RANK_PREFIX + registrationCode,
//						typedTuple.getValue().toString());
//				insertToRedisIntegralRank(registrationCode, todayIntegral, userId);
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @description 向redis排行榜的set中添加数据
//	 * @param registrationCode           租户id
//	 * @param userIntegralRankSet        排行榜set集合
//	 * @param goUserEmissionIntegralBean 每日减排，积分实体类
//	 * @throws Exception
//	 * @return BaseResponse
//	 * @author 江辉
//	 * @date 2019/07/05
//	 * @modifyDescription
//	 * @modifyDate
//	 */
//	private void insertToRedisIntegralRank(String registrationCode, Integer integral, Integer userId) {
//		redisTimeplateUtil.sortSetAdd(IntegralConstant.USER_TODAY_INTEGRAL_RANK_PREFIX + registrationCode,
//				String.valueOf(userId), integral + 0.0);
//	}
//
//}
