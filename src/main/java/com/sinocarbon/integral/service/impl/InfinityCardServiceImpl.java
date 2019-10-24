package com.sinocarbon.integral.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.sinocarbon.integral.constant.ErrorCode;
import com.sinocarbon.integral.constant.IntegralConstant;
import com.sinocarbon.integral.dao.IntegralLogDao;
import com.sinocarbon.integral.pojo.IntegralLog;
import com.sinocarbon.integral.pojo.IntegralLogAndTypeNamePOJO;
import com.sinocarbon.integral.service.InfinityCardService;
import com.sinocarbon.integral.utils.HeaderUtil;
import com.sinocarbon.integral.utils.RedisUtil;
import com.sinocarbon.polaris.commons.pojo.LoginAppUser;
import com.sinocarbon.polaris.commons.utils.BaseResponse;
import com.sinocarbon.polaris.commons.utils.PublicCommonsUtils;

@Service
public class InfinityCardServiceImpl extends ServiceImpl<IntegralLogDao, IntegralLog>
		implements InfinityCardService {


	@Autowired
	private RedisUtil redisTimeplateUtil;

	@Override
	public BaseResponse listInfinityCard() {
		
		LoginAppUser loginAppUser=PublicCommonsUtils.getLoginAppUser();

		String registrationCode = loginAppUser.getTenantId();

		// 获取userId
		Integer userId =  HeaderUtil.getWxUserId();
		// 先查询redis中有没有数据
		@SuppressWarnings("unchecked")
		HashMap<String,Integer> infinityCardMap = (HashMap<String, Integer>) redisTimeplateUtil.hget(IntegralConstant.INFINITY_CARD_MAP, userId+"");
		if(infinityCardMap==null||infinityCardMap.isEmpty()) {
			if(infinityCardMap==null) {infinityCardMap=new HashMap<>();}
			//数据库中查询数据并放入redis中
			ArrayList<IntegralLogAndTypeNamePOJO> infinityCardList = baseMapper.countLastWeekInfinityCard(userId,registrationCode);
			if(infinityCardList==null||infinityCardList.isEmpty()) {
				return BaseResponse.failed(ErrorCode.CODE_2003005, ErrorCode.CODE_2003005_MSG);
			}
			for(int i = 0;i<infinityCardList.size();i++) {
			String color=infinityCardList.get(i).getOperationColor();
			Integer integral=infinityCardList.get(i).getIntegral();
			
				if(color.equals("#FF7C46")||color.equals("#BB6ACF")) {//为了美观性人为扩大卡片登录和早起打卡显示积分
					integral=integral*10;
				}
				infinityCardMap.put(color, integral);
			}
			redisTimeplateUtil.hset(IntegralConstant.INFINITY_CARD_MAP, userId+"", infinityCardMap);
		}
		
		return BaseResponse.successed(infinityCardMap);
	}

}
