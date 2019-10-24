package com.sinocarbon.integral.pojo;

import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 积分日志表
 * </p>
 *
 * @author 葛明明123
 * @since 2019-06-26
 */
@Data
@TableName("integral_log")
public class IntegralLog implements Serializable {

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
     * 获取的积分
     */
    @TableField("integral")
    private Integer integral;
    
    /**
     * 任务id
     */
    @TableField("task_copy_id")
    private Integer taskCopyId;
    
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
