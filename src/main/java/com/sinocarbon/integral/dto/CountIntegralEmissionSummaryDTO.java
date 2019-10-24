package com.sinocarbon.integral.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
/**
 * 
 * 每日汇总DTO
 *
 */
public class CountIntegralEmissionSummaryDTO {
	
	@ApiModelProperty(value = "每日总减排量",dataType="double")
	private Double emissionReduction;
	@ApiModelProperty(value = "步行每日总减排量",dataType="double")
	private Double walkEmissionReduction;
	@ApiModelProperty(value = "步行步数",dataType="int")
	private Integer walkStep;
	@ApiModelProperty(value = "总积分",dataType="int")
	private Integer integral;
	@ApiModelProperty(value = "骑行每日总减排量",dataType="double")
	private Double bikeEmissionReduction;
	@ApiModelProperty(value = "骑行每日总公里数",dataType="double")
	private Double bikeKilometre;
	@ApiModelProperty(value = "签到积分",dataType="int")
	private Integer signIntegral;
	@ApiModelProperty(value = "登录积分",dataType="int")
	private Integer loginIntegral;
	@ApiModelProperty(value = "兑换积分",dataType="int")
	private Integer walkIntegral;
	@ApiModelProperty(value = "骑行积分",dataType="int")
	private Integer bikeIntegral;
	@ApiModelProperty(value = "挑战积分",dataType="int")
	private Integer challengeIntegral;
	@ApiModelProperty(value = "中创豆",dataType="double")
	private Double bean;
	
}
