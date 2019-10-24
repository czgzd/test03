package com.sinocarbon.integral.dto;

import lombok.Data;

/**
 * 日记柱状图DTO
 * @author gemingming
 *
 */
@Data
public class ShowSportlistDTO {
	
	private Integer typeId;
	private String typeColor;
	
	private Integer[] dataList;
	
	
	
}
