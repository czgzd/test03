package com.sinocarbon.integral.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * <p>
 * 减排量日志表
 * </p>
 *
 * @author 葛明明123
 * @since 2019-06-26
 */
@Data
@TableName("integral_emission_reduction_log")
public class IntegralEmissionReductionLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private long id;
	@TableField("user_id")
	private long userId;
	/**
	 * 操作类型Id
	 */
	@TableField("operation_type_id")
	private Integer operationTypeId;
	/**
	 * 减排量
	 */
	@TableField("emission_reduction")
	private Double emissionReduction;

	@TableField("gmt_create")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime gmtCreate;

	@TableField("gmt_modified")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime gmtModified;
	/**
	 * 注册码
	 */
	@TableField("registration_code")
	private String registrationCode;

}
