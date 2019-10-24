package com.sinocarbon.integral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinocarbon.integral.service.InfinityCardService;
import com.sinocarbon.polaris.commons.utils.BaseResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/integral")
@Api(value = "用户无极卡信息api")
public class InfinityCardController {

	@Autowired
	private InfinityCardService infinityCardService;

	/**
	 * 
	 * @description 获取用户无极卡信息
	 * @param trdSession api用户秘钥
	 * @return BaseResponse
	 * @author 江辉
	 * @date 2019/07/12
	 * @modifyDescription
	 * @modifier
	 * @modifyDate
	 * 
	 */
	//@PreAuthorize("hasAuthority('integral-service:get/api/integral/infinity/card')")
	@GetMapping(value = "/infinity/card")
	@ApiOperation(value = "getInfinityCard", notes = "获取用户无极卡信息")

	public BaseResponse getInfinityCard() {
		return infinityCardService.listInfinityCard();
	}
}
