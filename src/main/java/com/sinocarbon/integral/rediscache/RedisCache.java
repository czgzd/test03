package com.sinocarbon.integral.rediscache;


import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sinocarbon.integral.constant.IntegralConstant;
import com.sinocarbon.integral.dao.UserAccountInfoDao;
import com.sinocarbon.integral.utils.RedisUtil;

@Component
public class RedisCache {

	  
		@Autowired
		UserAccountInfoDao userIntegralDao;
		
		@Autowired
		RedisUtil redisUtil;
		
	
		
		
		
		
		
		
		/**
		 * 
		 * @description 每天0:20清空每日积分排行榜redis数据
		 * @throws Exception
		 * @return void   
		 * @author wangxiaowei 
		 * @date 2019/07/15
		 * @modifyDescription  
		 * @modifier xxx
		 * @modifyDate 修改日期
		 */
		@Scheduled(cron="0 20 0 * * ? ")
		public void clearTodayIntegralRankList() {
			
			  // 获取所有租户的sortSet名称
			  Set<String> keys= redisUtil.getKeysByPrex(IntegralConstant.USER_TODAY_INTEGRAL_RANK_PREFIX);
				 
		       // 删除所有租户对应的积分集合（sortSet）
	           redisUtil.delkeysBatch(keys);
		}

		/**
		 * 
		 * @description 每天0点30清空用户当天减排和积分数据 
		 * @throws Exception
		 * @return void   
		 * @author wangxiaowei 
		 * @date 2019/07/15
		 * @modifier xxx
		 * @modifyDate 修改日期
		 */
		@Scheduled(cron="0 30 0 * * ? ")
		public void clearTotalIntegralRankList() {
			
			redisUtil.delKeys(IntegralConstant.USER_TODAY_INTEGRAL_EMISSION_MAP);
			
		}
		
		

		
		
		
		
}
