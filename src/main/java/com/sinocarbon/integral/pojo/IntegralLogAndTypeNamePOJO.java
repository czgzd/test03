package com.sinocarbon.integral.pojo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class IntegralLogAndTypeNamePOJO {

	private Integer integral;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime gmtCreate;
	private Integer operationTypeId;
	private String operationName;
	private String operationPictureUrl;
	private String operationColor;

}
