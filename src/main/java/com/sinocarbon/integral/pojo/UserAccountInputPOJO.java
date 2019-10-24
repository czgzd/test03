package com.sinocarbon.integral.pojo;

import java.io.Serializable;


import lombok.Data;

@Data
public class UserAccountInputPOJO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName;
	private String userNickName;
	private Integer minIntegral;
	private Integer id;
	private Integer maxIntegral;
	private Integer minExchangeIntegral;
	private Integer maxExchangeIntegral;
	private Integer minCarbonSave;
	private Integer maxCarbonSave;
    private String registrationCode;

	
}
