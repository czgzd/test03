package com.sinocarbon.integral.utils;

public class IsNotEmptyUtil {
	
	/**
	 * 
	 * @description 批量判断参数是否为空
	 * @param strs 参数
	 * @return boolean   
	 * @author gemingming 
	 * @date 2019/07/03
	 * @modifyDescription 
	 * @modifier
	 * @modifyDate
	 */
	public static boolean isNotEmptyBatch( Object... strs) {
		for (int i = 0; i < strs.length; i++) {
			Object string = strs[i];
			if (string == null || "".equals(string)) {
				return false;
			}
		
		}
		return true;
	}

}
