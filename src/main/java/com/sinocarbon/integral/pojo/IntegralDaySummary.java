package com.sinocarbon.integral.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@TableName("integral_day_summary")
@Data
public class IntegralDaySummary implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private long id;

	/*
	 * 用户id
	 */
	@TableField("user_id")
	private long userId;
	/*
	 * 总减排量
	 */
	@TableField("emission_reduction")
	private Double emissionReduction;
	/*
	 * 步行每日的总减排量
	 */
	@TableField("walk_emission_reduction")
	private Double walkEmissionReduction;
	
	
	@TableField("walk_step")
	private Integer walkStep;
	/*
	 * 骑行每日的总减排量
	 */
	@TableField("bike_emission_reduction")
	private Double bikeEmissionReduction;
	
	@TableField("bike_kilometre")
	private Double bikeKilometre;
	/*
	 * 每日产生的积分
	 */
	@TableField("integral")
	private Integer integral;
	
	@TableField("sign_integral")
	private Integer signIntegral;
	
	@TableField("login_integral")
	private Integer loginIntegral;
	
	@TableField("walk_integral")
	private Integer walkIntegral;
	
	@TableField("bike_integral")
	private Integer bikeIntegral;
	
	@TableField("challenge_integral")
	private Integer challengeIntegral;
	@TableField("bean")
	private Double bean;
	
	@TableField("consumption_bean")
	private Double consumptionBean;
	
	@TableField("exchanges_number")
	private Integer exchangesNumber;

	@TableField("gmt_create")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime gmtCreate;

	@TableField("gmt_modified")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime gmtModified;

	@TableField("registration_code")
	private String registrationCode;
	
	@TableField("surplus_total_bean")
	private Double surplusTotalBean;
	
	@TableField("consumption_total_bean")
	private Double consumptionTotalBean;
	

}
