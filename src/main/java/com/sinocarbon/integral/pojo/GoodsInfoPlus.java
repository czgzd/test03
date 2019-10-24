package com.sinocarbon.integral.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * <p>
 * 商品信息+分类分组信息
 * </p>
 *
 * @author 葛明明123
 * @since 2019-08-12
 */
@Data
public class GoodsInfoPlus implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
    private Integer id;
    private String goodsName;
    private String goodsCode;
    private Integer defaultBean;
    private String goodsDescription;
    private String goodsSummary;
    private Integer totalStock;
    private Integer goodsState;
    private Integer isSku;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private String registrationCode;
    private Integer typeId;
    private Integer groupId;
    private Integer salesVolume;
    private String imageUrl;
    private List<GoodsImage> goodsImageList;
}
