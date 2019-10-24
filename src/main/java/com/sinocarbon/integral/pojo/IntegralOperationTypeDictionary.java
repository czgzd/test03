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
 * 积分获取途径类型表
 * </p>
 *
 * @author 葛明明123
 * @since 2019-06-26
 */
@Data
@TableName("integral_operation_type")
public class IntegralOperationTypeDictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    /**
     * 操作名称
     */
    @TableField("operation_name")
    private String operationName;
    /**
     * 操作图片
     */
    @TableField("operation_picture_url")
    private String operationPictureUrl;
    /**
     * 操作颜色
     */
    @TableField("operation_color")
    private String operationColor;
    

    
	@TableField("gmt_create")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime gmtCreate;
	
	@TableField("gmt_modified")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime gmtModified;

	@TableField("registration_code")
    private String registrationCode;
   
}
