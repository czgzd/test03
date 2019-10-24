package com.sinocarbon.integral.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 积分日志DTO
 * @author gemingming
 *
 */
@Data
@ApiModel
public class IntegralLogAndTypeNameDTO {
	
	@ApiModelProperty(value = "积分", dataType = "int")
	private Integer integral;
	@ApiModelProperty(value = "更新时间", dataType = "String")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;
	@ApiModelProperty(value = "操作名称", dataType = "String")
	private String operationName;
	@ApiModelProperty(value = "操作类型颜色", dataType = "String")
	private String operationColor;
	@ApiModelProperty(value = "操作类型图片", dataType = "String")
	private String operationPictureUrl;
}
