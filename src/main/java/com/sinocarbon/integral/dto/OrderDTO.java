package com.sinocarbon.integral.dto;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel
public class OrderDTO {
	
	@ApiModelProperty(value = "订单id",required=false,dataType="int")
	private Integer id;
	
	@ApiModelProperty(value = "订单状态",required=false,dataType="int")
	private Integer orderState; 
	
	@ApiModelProperty(value = "商品购买数量",required=false,dataType="Map")
	private  Map<Integer, Integer> goodsMap;
	
	@ApiModelProperty(value = "支付积分",required=false,dataType="int")
	private Integer payIntegral;
	
	@ApiModelProperty(value = "支付数量",required=false,dataType="int")
	private Integer payNum;
	
	@ApiModelProperty(value = "收获方式",required=false,dataType="int")
	private Integer receivingMethod;
	
	@ApiModelProperty(value = "用户留言",required=false,dataType="String")
	private String userMessage;
	
	
	@ApiModelProperty(value = "是否是购物车结账",required=false,dataType="boolean")
	private Boolean isCart;
	
	@ApiModelProperty(value = "商品id字符串",required=false,dataType="String")
	private String goodsIdStr;
	
	@ApiModelProperty(value = "订单状态数量统计",required=false,dataType="int")
	private Integer stateCount;
	
	
}
