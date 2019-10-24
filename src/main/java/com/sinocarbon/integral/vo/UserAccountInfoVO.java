package com.sinocarbon.integral.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;


@ApiModel
public class UserAccountInfoVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userNickName;
	private String userName;
	private String userSignature;
	private String departmentName;
	private String jobName;
	private String userImg;
	private String organizationName;
	private Integer onLineDays;
	private Integer walkStep;
	private Double bikeKilometre;
	private Double emissionReduction;
	private Integer integral;
	private Double totalBean;
	private Integer exchangesNumber;
	private Double bean;
	private Double consumptionIntegral;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserSignature() {
		return userSignature;
	}
	public void setUserSignature(String userSignature) {
		this.userSignature = userSignature;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public Integer getOnLineDays() {
		return onLineDays;
	}
	public void setOnLineDays(Integer onLineDays) {
		this.onLineDays = onLineDays;
	}
	public Integer getWalkStep() {
		return walkStep;
	}
	public void setWalkStep(Integer walkStep) {
		this.walkStep = walkStep;
	}
	public Double getBikeKilometre() {
		return bikeKilometre;
	}
	public void setBikeKilometre(Double bikeKilometre) {
		this.bikeKilometre = bikeKilometre;
	}
	public Double getEmissionReduction() {
		return emissionReduction;
	}
	public void setEmissionReduction(Double emissionReduction) {
		this.emissionReduction = emissionReduction;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public Double getTotalBean() {
		return totalBean;
	}
	public void setTotalBean(Double totalBean) {
		this.totalBean = totalBean;
	}
	public Integer getExchangesNumber() {
		return exchangesNumber;
	}
	public void setExchangesNumber(Integer exchangesNumber) {
		this.exchangesNumber = exchangesNumber;
	}
	public Double getBean() {
		return bean;
	}
	public void setBean(Double bean) {
		this.bean = bean;
	}
	public Double getConsumptionIntegral() {
		return consumptionIntegral;
	}
	public void setConsumptionIntegral(Double consumptionIntegral) {
		this.consumptionIntegral = consumptionIntegral;
	}
	@Override
	public String toString() {
		return "UserAccountInfoVO [id=" + id + ", userNickName=" + userNickName + ", userName=" + userName
				+ ", userSignature=" + userSignature + ", departmentName=" + departmentName + ", jobName=" + jobName
				+ ", userImg=" + userImg + ", organizationName=" + organizationName + ", onLineDays=" + onLineDays
				+ ", walkStep=" + walkStep + ", bikeKilometre=" + bikeKilometre + ", emissionReduction="
				+ emissionReduction + ", integral=" + integral + ", totalBean=" + totalBean + ", exchangesNumber="
				+ exchangesNumber + ", bean=" + bean + ", consumptionIntegral=" + consumptionIntegral + "]";
	}
	
	
	
	
}
