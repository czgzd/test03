package com.sinocarbon.integral.service;

import com.baomidou.mybatisplus.service.IService;
import com.sinocarbon.integral.pojo.IntegralLog;
import com.sinocarbon.polaris.commons.utils.BaseResponse;

public interface InfinityCardService extends IService<IntegralLog> {

	BaseResponse listInfinityCard();

}
