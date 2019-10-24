package com.sinocarbon.integral.rediscache;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sinocarbon.integral.client.rest.service.ConfigCenterService;
import com.sinocarbon.integral.dao.IntegralDaySummaryDao;
import com.sinocarbon.integral.pojo.IntegralDaySummary;
import com.sinocarbon.integral.pojo.UserAccountInfo;
import com.sinocarbon.integral.pojo.UserSurplusBeanPOJO;
import com.sinocarbon.integral.service.UserAccountInfoService;
import com.sinocarbon.integral.utils.DateUtils;
import com.sinocarbon.polaris.commons.utils.BaseResponse;



@Component
public class UserAccountInfoServiceTask {

	@Autowired
	IntegralDaySummaryDao integralDaySummaryDao;
	
	@Autowired
	UserAccountInfoService  userAccountInfoService;
	
	@Autowired
	ConfigCenterService configSertvice;
	

	
	
	/**
	 * 
	 * @description 每天凌晨0点30计算无极豆
	 * @param moneyLimit 每天的人民币额度
	 * @param scale   兑换比例
	 * @throws Exception
	 * @return void   
	 * @author wangxiaowei 
	 * @date 2019/07/10
	 * @modifyDescription 
	 * @modifyDate 修改日期
	 */
	@Scheduled(cron="0 30 0 * * ? ")
	@Transactional(rollbackFor=Exception.class)
	public void  calculateBeanOnTime() {
		//先跟新每日表豆子，在跟新总表的积分和中创豆（for循环跟新）
		
		//计算中创豆并入库
		calculateUserBean(DateUtils.getYesterdayStr("yyyy-MM-dd"));
		
	}
	
	/**
	 * 
	 * @description 中创豆汇总
	 * @param moneyLimit 每天的人民币额度
	 * @param scale   兑换比例
	 * @param dateStr  日期字符串  格式  2019-09-09
	 * @throws Exception
	 * @return void   
	 * @author wangxiaowei 
	 * @date 2019/07/10
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
    @SuppressWarnings("rawtypes")
	public void calculateUserBean(String dateStr){
    	
    	
    	//昨日总积分-按租户分组
    	List<Map> countYesterdayIntegral = integralDaySummaryDao.countSomedayUserIntegral(dateStr);
    	// 组装数据
    	HashMap<String, Integer> packageDataToMap = (HashMap<String, Integer>) packageDataToMap(countYesterdayIntegral);
    	
    	//昨日产生积分的用户
    	List<IntegralDaySummary> integralDaySummaryList = integralDaySummaryDao.listAllRecordByDateStr(dateStr);
    	
    	//计算用户的中创豆并入库
    	loopsUpdateUserBean(integralDaySummaryList, packageDataToMap);
    	
    	
    }
	
	/**
	 * 计算积分折算中创豆系数
	 * @param moneyLimit 额度
	 * @param scale  比例
	 * @param integralSum 当日总积分
	 * @throws Exception
	 * @return Double 积分系数  
	 * @author wangxiaowei 
	 * @date 2019/07/10
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
	public Double  calculateBeanCoefficient(Integer moneyLimit,Double scale,Integer integralSum ) {
		
		
		return moneyLimit*scale/integralSum;
		
	}
	
	
	/**
	 * 组装数据
	 * @param dataMap 当日积分和
	 * @throws Exception
	 * @return HashMap<String,Integer> 组装数据  key--注册码，value--对应用户一天的积分和
	 * @author wangxiaowei 
	 * @date 2019/07/10
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
   @SuppressWarnings("rawtypes")
public Map<String, Integer> packageDataToMap(List<Map> dataMap){
	 
	    HashMap<String, Integer> packageDataMap = new HashMap<>();
	    Map tempMap ;
	   for(int i =0;i<dataMap.size();i++) {
		   tempMap = dataMap.get(i);
		   packageDataMap.put(tempMap.get("registration_code").toString(), Integer.valueOf(tempMap.get("integralSum").toString()));
		   
	   }
	   
	   return packageDataMap;
	   
   }
	
	/**
	 * 
	 * @description 更新用户每日中创豆
	 * @param entity 实体类
	 * @throws Exception
	 * @return void   
	 * @author wangxiaowei 
	 * @date 2019/07/10
	 * @modifier xxx
	 * @modifyDate 修改日期
	 */
   public void updateUserBean(IntegralDaySummary entity) {
	   integralDaySummaryDao.updateById(entity);
	   
   }
   
   /**
    * 
    * @description 循环更新用户无极豆信息 
    * @param userBeanList  用户每日减排 积分 记录
    * @param  integralMap  每个租户下用户积分和 map
    * @param  moneyLimit 额度
    * @param  scale 比例
    * @throws Exception
    * @return void   
    * @author wangxiaowei 
    * @date 2019/07/10
    * @modifier xxx
    * @modifyDate 修改日期
    */
   @SuppressWarnings("unlikely-arg-type")
public void loopsUpdateUserBean(List<IntegralDaySummary> integralDaySummaryList,Map<String, Integer> integralMap) {
	   
	   String registrationCode ;
	   IntegralDaySummary integralDaySummary ;
	   Double userBeans ;
	   Double integralCoefficient ;
	   BigDecimal bd;
	   UserAccountInfo integralEmissionUser ;
	   Integer moneyLimit;
	   BaseResponse base;
	   Double scale;
	   HashMap<String, Integer> budgetMap = new HashMap<>();
	   
		Map<String, Double> scaleMap = configSertvice.getScale();
		
		Map<Integer, UserSurplusBeanPOJO> packageUserBeanMap = packageUserBean();
		 UserSurplusBeanPOJO beanPOJO ;
		 Double surplusTotalBean;
		 Double consumptionTotalBean;
	   
	   for(int i = 0;i<integralDaySummaryList.size();i++) {
		   integralDaySummary = integralDaySummaryList.get(i);
		   registrationCode = integralDaySummary.getRegistrationCode();
		   
		   //判空
		   if(integralMap.get(registrationCode)==null||integralMap.get(registrationCode)==0) {
			   continue;
		   }
		  if(budgetMap.get(registrationCode)==null) {
			//获取额度
			   base =  configSertvice.getBudget(registrationCode);
			   moneyLimit =Integer.valueOf(base.getData().toString()) ;
			   
			   budgetMap.put(registrationCode, moneyLimit);
		  }else {
			  moneyLimit = budgetMap.get(registrationCode);
		  }
		  
		  if(moneyLimit==0) {//预算为0 则跳过
			   continue;
		   }
		  scale = scaleMap.get(registrationCode);
		   
		   integralCoefficient =  calculateBeanCoefficient(moneyLimit, scale, integralMap.get(registrationCode));
		   
		   userBeans = integralDaySummary.getIntegral()*integralCoefficient;
		   bd = new BigDecimal(""+userBeans);
		   integralDaySummary.setBean(Double.valueOf(bd.setScale(4,RoundingMode.DOWN).toPlainString()));//保留4位，不四舍五入直接截取
		   
		   //更新累计消费代币和用户剩余代币(用户剩余 = 当前剩余的+当日产生的)
		   beanPOJO = packageUserBeanMap.get((int)integralDaySummary.getUserId());
		   
		   if(beanPOJO==null) {//没有用户信息，第一天登陆
			   surplusTotalBean = userBeans;
			   consumptionTotalBean = 0.0;
		   }else {
			   surplusTotalBean = (beanPOJO.getSurplusBean()==null?0.0:beanPOJO.getSurplusBean())+integralDaySummary.getBean();
			   consumptionTotalBean = beanPOJO.getPayBean()==null?0.0:beanPOJO.getPayBean();
		   }
		   
		   
		   integralDaySummary.setSurplusTotalBean(surplusTotalBean);
		   integralDaySummary.setConsumptionTotalBean(consumptionTotalBean);
		   
		   updateUserBean(integralDaySummary);//更新每日表bean
		   
		   
		   
		   //更新积分总积分
		    integralEmissionUser = new UserAccountInfo();
			
			integralEmissionUser.setUserId(integralDaySummary.getUserId());
			integralEmissionUser.setEmissionReduction(integralDaySummary.getEmissionReduction()==null?0.0:integralDaySummary.getEmissionReduction());
			integralEmissionUser.setIntegral(integralDaySummary.getIntegral()==null?0:integralDaySummary.getIntegral());
			integralEmissionUser.setBean(integralDaySummary.getBean()==null?0:integralDaySummary.getBean());
			integralEmissionUser.setTotalBean(integralDaySummary.getBean()==null?0:integralDaySummary.getBean());
			integralEmissionUser.setRegistrationCode(registrationCode);
			
			integralEmissionUser.setBikeKilometre(integralDaySummary.getBikeKilometre()==null?0.0:integralDaySummary.getBikeKilometre());
			integralEmissionUser.setBikeIntegral(integralDaySummary.getBikeIntegral()==null?0:integralDaySummary.getBikeIntegral());
			integralEmissionUser.setWalkStep(integralDaySummary.getWalkStep()==null?0:integralDaySummary.getWalkStep());
			integralEmissionUser.setWalkIntegral(integralDaySummary.getWalkIntegral()==null?0:integralDaySummary.getWalkIntegral());
			integralEmissionUser.setOnLineDays(1); //上线天数为1
			if(integralDaySummary.getSignIntegral()==null) {
				integralEmissionUser.setSignIntegral(0);
				integralEmissionUser.setSignNumber(0);//
			}else {
				integralEmissionUser.setSignIntegral(integralDaySummary.getSignIntegral());
				integralEmissionUser.setSignNumber(1);//
			}
			
			integralEmissionUser.setLoginIntegral(integralDaySummary.getLoginIntegral()==null?0:integralDaySummary.getLoginIntegral());
			integralEmissionUser.setExchangesNumber(integralDaySummary.getExchangesNumber()==null?0:integralDaySummary.getExchangesNumber());
			
		   
			userAccountInfoService.updateOrInsertUserIntegralAndBean(integralEmissionUser);
		   
	   }
	   
	
	   
	   
   }
   

   /**
    * 
    * @description 组装用户剩余代币以及 以及累计消费代币,	截止到昨日
    * @throws Exception
    * @return Integer   
    * @author wangxiaowei 
    * @date 2019/10/21
    * @modifier xxx
    * @modifyDate 修改日期
    */
   public  Map<Integer, UserSurplusBeanPOJO> packageUserBean() {
	   
	   HashMap<Integer, UserSurplusBeanPOJO> dataMap = new HashMap<>();
	   UserSurplusBeanPOJO beanPOJO ;
	   
	   List<UserSurplusBeanPOJO> beanDataList = integralDaySummaryDao.listBeanData();
       for(int i = 0;i<beanDataList.size();i++) {
    	   beanPOJO = beanDataList.get(i);
    	   dataMap.put(beanPOJO.getUserId(), beanPOJO);
       }
	   
	   
	   return dataMap;
   }
   
   
   
}
