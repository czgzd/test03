package com.sinocarbon.integral.pojo;


import lombok.Data;

@Data
public class UserIntegralDaySummaryPOJO {
	private long id;
	private Integer userId;
	private String headImg;
	private Integer userGender;
	private String userNickName;
	private String userSignature;
	private Integer integral;
	private	Double emissionReduction;
	private	Double walkEmissionReduction;
	private	Integer walkStep;
	private	Double bikeEmissionReduction;
	private	Double bikeKilometre;
	private	Integer signIntegral;
	private	Integer loginIntegral;
	private	Integer walkIntegral;
	private	Integer bikeIntegral;
	private	Integer challengeIntegral;
	private Double bean;
	private Double consumptionBean;
	private Integer exchangesNumber;
	
	
}
