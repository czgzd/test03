//package com.sinocarbon.integral.service.impl;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.baomidou.mybatisplus.plugins.pagination.Pagination;
//import com.baomidou.mybatisplus.service.impl.ServiceImpl;
//import com.sinocarbon.integral.constant.ErrorCode;
//import com.sinocarbon.integral.constant.IntegralConstant;
//import com.sinocarbon.integral.dao.IntegralLogDao;
//import com.sinocarbon.integral.dto.IntegralLogAndTypeNameDTO;
//import com.sinocarbon.integral.dto.ShowSportlistDTO;
//import com.sinocarbon.integral.pojo.IntegralLog;
//import com.sinocarbon.integral.pojo.IntegralLogAndTypeNamePOJO;
//import com.sinocarbon.integral.service.IntegralLogService;
//import com.sinocarbon.integral.utils.DateUtils;
//import com.sinocarbon.integral.utils.HeaderUtil;
//import com.sinocarbon.integral.utils.RedisUtil;
//import com.sinocarbon.integral.utils.TransformationBeansUtil;
//import com.sinocarbon.polaris.commons.pojo.LoginAppUser;
//import com.sinocarbon.polaris.commons.utils.BaseResponse;
//import com.sinocarbon.polaris.commons.utils.PublicCommonsUtils;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * <p>
// * 积分日志表 服务实现类
// * </p>
// *
// * @author 葛明明123
// * @since 2019-06-26
// */
//@Service
//@Slf4j
//public class IntegralLogServiceImpl extends ServiceImpl<IntegralLogDao, IntegralLog> implements IntegralLogService {
//
//	@Autowired
//	private RedisUtil redisTimeplateUtil;
//
//	@Autowired
//	private IntegralLogDao integralLogDao;
//	
//
//
//	/**
//	 * 
//	 * @description 个人积分日志查询
//	 * @param trdSession 用户信息秘钥
//	 * @param pageNum    当前页码
//	 * @param pageSize   每页条数
//	 * @throws Exception
//	 * @return BaseResponse
//	 * @author 江辉
//	 * @date 2019/07/11
//	 * @modifyDescription
//	 * @modifier
//	 * @modifyDate
//	 */
//	@Override
//	public BaseResponse listUserIntegralLog( Integer serchType, Integer pageNum, Integer pageSize,Integer operationTypeId) {
//	
//		List<IntegralLogAndTypeNameDTO> userIntegralLogDTO = new ArrayList<>();
//		List<IntegralLogAndTypeNamePOJO> userIntegralLog;
//		
//		LoginAppUser loginAppUser=PublicCommonsUtils.getLoginAppUser();
//
//		String registrationCode = loginAppUser.getTenantId(); 
//		
//		if (pageNum == null || pageNum == 0) {
//			pageNum = IntegralConstant.DEFAULT_PAGE_NUMBER;
//		}
//		if (pageSize == null || pageSize == 0) {
//			pageSize = IntegralConstant.DEFAULT_PAGE_SIZE;
//		}
//		Integer userId =  HeaderUtil.getWxUserId();
//		Pagination page = new Pagination(pageNum, pageSize);
//		userIntegralLog = integralLogDao.listIntegralLog(userId, serchType, page,operationTypeId,registrationCode);
//		TransformationBeansUtil.populateList(userIntegralLog, userIntegralLogDTO, IntegralLogAndTypeNameDTO.class);
//		return BaseResponse.successed(userIntegralLogDTO);
//	}
//
//	
//
//	/**
//	 * 
//	 * @description 查询日记
//	 * @param serchType  1:本周 2:上周
//	 * @throws Exception
//	 * @return BaseResponse
//	 * @author 王小伟
//	 * @date 2019/07/21
//	 * @modifyDescription
//	 * @modifier
//	 * @modifyDate
//	 */
//	@SuppressWarnings("rawtypes")
//	@Override
//	public BaseResponse listWeekDiary( Integer serchType) {
//		
//		LoginAppUser loginAppUser=PublicCommonsUtils.getLoginAppUser();
//
//		String registrationCode = loginAppUser.getTenantId();
//		
//		BaseResponse res = judgeData( serchType);
//		if(res!=null) {
//			return res;
//		}
//		
//		
//		Integer userId =  HeaderUtil.getWxUserId();
//		List<IntegralLogAndTypeNamePOJO> integralLogList;
//		List<IntegralLogAndTypeNameDTO> integralLogListDTOList=new ArrayList<>();
//		if (serchType == 1) {
//			// redis中取 取不到就是去数据库拿 再放入redis中
//			integralLogListDTOList = redisTimeplateUtil.hgetListByJson(IntegralConstant.USER_WEEK_DIARY_MAP, userId.toString(),
//					IntegralLogAndTypeNameDTO.class);
//			if (integralLogListDTOList == null || integralLogListDTOList.isEmpty()) {
//
//				integralLogList = integralLogDao.listIntegralLog(userId, serchType,null,registrationCode);
//				if (integralLogList == null) {
//					BaseResponse.failed(ErrorCode.CODE_2003005, ErrorCode.CODE_2003005_MSG);
//				}
//				TransformationBeansUtil.populateList(integralLogList, integralLogListDTOList, IntegralLogAndTypeNameDTO.class);
//				redisTimeplateUtil.hsetJson(IntegralConstant.USER_WEEK_DIARY_MAP, userId + "", integralLogListDTOList);
//			}
//
//		} else {
//			// 从数据库取上周的数据
//			integralLogList = integralLogDao.listIntegralLog(userId, serchType,null,registrationCode);
//			TransformationBeansUtil.populateList(integralLogList, integralLogListDTOList, IntegralLogAndTypeNameDTO.class);
//
//		}
//
//		// 柱状图数据
//		List<IntegralLogAndTypeNamePOJO> listUesrIntegarlRecords = integralLogDao.listIntegralLogGroup(userId, serchType,registrationCode);
//
//		List<ShowSportlistDTO> diaryList = packageData(listUesrIntegarlRecords);
//
//		HashMap<String, List> dataMap = new HashMap<>();
//		dataMap.put("logList", integralLogListDTOList);
//		dataMap.put("barList", diaryList);
//
//		return BaseResponse.successed(dataMap);
//	}
//
//	/**
//	 * 
//	 * @description 组装数据
//	 * @param sportList 柱状图数据列表
//	 * @throws Exception
//	 * @return List<Map>
//	 * @author wangxiaowei
//	 * @date 2019/07/21
//	 * @modifier xxx
//	 * @modifyDate 修改日期
//	 */
//	private List<ShowSportlistDTO> packageData(List<IntegralLogAndTypeNamePOJO> sportList) {
//
//		ShowSportlistDTO temp;
//		List<ShowSportlistDTO> diaryList = new ArrayList<>();
//
//		Integer index;
//		Integer[] dataArray;
//		Integer[] tempArray = null;
//		for (int i = 0; i < sportList.size(); i++) {
//			String dateStr=DateUtils.getDateStr(sportList.get(i).getGmtCreate());
//			index = getWeekDay(dateStr);
//			for (int j = 0; j < diaryList.size(); j++) {
//				temp = diaryList.get(j);
//				if (sportList.get(i).getOperationTypeId() == temp.getTypeId()) {// 同一个类型，一天只有一条总和数据,一共七条数据
//
//					tempArray = temp.getDataList();
//					tempArray[index] = sportList.get(i).getIntegral();
//
//				}
//			}
//
//			if (tempArray == null) {// 不存在 ，新建一个类型类
//				temp = new ShowSportlistDTO();
//				dataArray = new Integer[] { 0, 0, 0, 0, 0, 0, 0 };
//				dataArray[index] = sportList.get(i).getIntegral();
//				temp.setDataList(dataArray);
//				temp.setTypeColor(sportList.get(i).getOperationColor());
//				temp.setTypeId(sportList.get(i).getOperationTypeId());
//				diaryList.add(temp);
//			} else {
//
//				tempArray = null;
//			}
//		}
//		return diaryList;
//	}
//
//	/**
//	 * 
//	 * @description 获取当前是周几 ，0-6，周一至周日，
//	 * @param timetr 时间串
//	 * @throws Exception
//	 * @return Integer
//	 * @author wangxiaowei
//	 * @date 2019/07/21
//	 * @modifier xxx
//	 * @modifyDate 修改日期
//	 */
//	private Integer getWeekDay(String timetr) {
//		SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
//		Date dateTime;
//		Integer index = -1;
//		try {
//			dateTime = sm.parse(timetr);
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(dateTime);
//			index = cal.get(Calendar.DAY_OF_WEEK);// 1-7=>周日-周六
//		} catch (ParseException e) {
//			log.error("日期转换异常", e);
//		}
//
//		index = index == 1 ? 6 : index - 2;
//
//		return index;
//
//	}
//
//	@Override
//	public BaseResponse getSignStatus() {
//		
//		Integer signStatus = 1;
//		IntegralLog integralLog = new IntegralLog();
//		integralLog.setUserId( HeaderUtil.getWxUserId());
//		integralLog.setOperationTypeId(IntegralConstant.OPERATION_SIGN);
//
//		integralLog = baseMapper.getToadyLastIntegralLog(integralLog);
//		if (integralLog == null) {
//			signStatus = 0;
//		}
//		return BaseResponse.successed(signStatus);
//	}
//
//	@Override
//	public BaseResponse getLoginStatus() {
//		Object userInfo=baseMapper.getAppUserInof(HeaderUtil.getWxUserId());
//		if(userInfo==null) {
//			return BaseResponse.failed(ErrorCode.CODE_2003005, ErrorCode.CODE_2003005_MSG);
//		}
//		Integer loginStatus = 1;
//		IntegralLog integralLog = new IntegralLog();
//		integralLog.setUserId( HeaderUtil.getWxUserId());
//		integralLog.setOperationTypeId(IntegralConstant.OPERATION_LOGIN);
//		
//		integralLog = baseMapper.getToadyLastIntegralLog(integralLog);
//		if (integralLog == null) {
//			loginStatus = 0;
//		}
//		return BaseResponse.successed(loginStatus);
//	}
//
//	/**
//	 * 
//	 * @description 判空 
//	 * @param trdSession  凭证
//	 * @param serchType  类型
//	 * @throws Exception
//	 * @return BaseResponse   
//	 * @author wangxiaowei 
//	 * @date 2019/08/22
//	 * @modifier xxx
//	 * @modifyDate 修改日期
//	 */
//	public BaseResponse judgeData(Integer serchType) {
//	
//		if (serchType != 1 && serchType != 2) {
//			return BaseResponse.failed(ErrorCode.CODE_2003006, ErrorCode.CODE_2003006_MSG);
//		}
//		
//		return null;
//	}
//	
//	
//}
