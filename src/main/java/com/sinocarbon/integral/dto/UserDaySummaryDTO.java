package com.sinocarbon.integral.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 每日信息DTO
 * @author gemingming
 *
 */
@Data
public class UserDaySummaryDTO {
	@ApiModelProperty(value = "用户Id",dataType="int")
	private Integer userId;
	@ApiModelProperty(value = "头像",dataType="string")
	private String headImg;
	@ApiModelProperty(value = "性别",dataType="int")
	private Integer userGender;
	@ApiModelProperty(value = "昵称",dataType="string")
	private String userNickName;
	@ApiModelProperty(value = "签名",dataType="string")
	private String userSignature;
	@ApiModelProperty(value = "积分",dataType="int")
	private Integer integral;
}
