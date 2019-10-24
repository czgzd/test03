package com.sinocarbon.integral.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 积分管理DTO
 * @author 吴健
 *
 */
@Data
@ApiModel
public class UserAccountInfoDTO {
	@ApiModelProperty(value = "员工",required = false,dataType="string")
	private String userName;
	@ApiModelProperty(value = "昵称",required = false,dataType="string")
	private String userNickName;
	@ApiModelProperty(value = "最低积分",required=false,dataType="int")
	private Integer minIntegral;
	@ApiModelProperty(value = "最高积分",required=false,dataType="int")
	private Integer maxIntegral;
	@ApiModelProperty(value = "最低总豆",required=false,dataType="int")
	private Integer minExchangeIntegral;
	@ApiModelProperty(value = "最高总豆",required=false,dataType="int")
	private Integer maxExchangeIntegral;
	@ApiModelProperty(value = "最低总节碳",required=false,dataType="int")
	private Integer minCarbonSave;
	@ApiModelProperty(value = "最高总节碳",required=false,dataType="int")
	private Integer maxCarbonSave;
}
