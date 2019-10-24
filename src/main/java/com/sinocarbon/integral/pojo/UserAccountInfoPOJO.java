package com.sinocarbon.integral.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * <p>
 * 积分管理信息
 * </p>
 *
 * @author 吴健
 * @since 2019-09-10
 */
@TableName("user_account_info")
@Data
public class UserAccountInfoPOJO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*
	 * 主键id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private long id;
	/*
	 * 会员昵称
	 */
	@TableField("user_nickname")
	private String userNickName;
	/*
	 * 员工
	 */
	@TableField("user_name")
	private String userName;
	/*
	 * 签名
	 */
	@TableField("user_signature")
	private String userSignature;
	/*
	 * 部门名称
	 */
	@TableField("department_name")
	private String departmentName;
	/*
	 * 职位
	 */
	@TableField("job_name")
	private String jobName;
	/*
	 * 头像
	 */
	@TableField("user_img")
	private String userImg;
	/*
	 * 组织名称
	 */
	@TableField("organization_name")
	private String organizationName;
	/*
	 * 在线天数
	 */
	@TableField("on_line_days")
	private Integer onLineDays;
	/*
	 * 步数
	 */
	@TableField("walk_step")
	private Integer walkStep;
	/*
	 * 骑行(km)
	 */
	@TableField("bike_kilometre")
	private Double bikeKilometre;
	/*
	 * 总减排量
	 */
	@TableField("emission_reduction")
	private Double emissionReduction;
	/*
	 * 总积分
	 */
	@TableField("integral")
	private Integer integral;
	/*
	 * 总豆（兑换积分）
	 */
	@TableField("total_bean")
	private Double totalBean;
	/*
	 * 兑换次数
	 */
	@TableField("exchanges_number")
	private Integer exchangesNumber;
	/*
	 * 余额豆（兑换积分）
	 */
	@TableField("bean")
	private Double bean;
	
	/*
	 * 消耗积分
	 */
	@TableField("consumption_integral")
	private Double consumptionIntegral;
	
	
	
}
