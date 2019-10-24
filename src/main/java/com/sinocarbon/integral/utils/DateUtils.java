package com.sinocarbon.integral.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.sinocarbon.integral.constant.ErrorCode;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DateUtils {

	


	/**
	 * 
	 * @description 获取当前时间
	 * @return String
	 * @author gemingming
	 * @date 2019/07/01
	 */
	public static LocalDateTime getLocalDate() {

		return LocalDateTime.now();
	}

	/**
	 * 
	 * @description 时间并转化为XX小时
	 * @return String
	 * @author gemingming
	 * @date 2019/07/01
	 */
	public static Integer getLocalHours(LocalDateTime localDateTime) {
	   DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("HH");
		return Integer.valueOf(localDateTime.format(dateTimeFormatter1));
	}

	/**
	 * 
	 * @description 按照指定格式获取昨天的日期字符串
	 * @param formastter 日期显示格式 如yyyy-MM-dd
	 * @throws Exception
	 * @return String 格式化后的日期字符串
	 * @author wangxiaowei
	 */
	public static String getYesterdayStr(String formastter) {

		return getLocalDate().minusDays(1).format(DateTimeFormatter.ofPattern(formastter));

	}
	
	
	/**
	 * 
	 * @description 按照指定格式获取某天的日期字符串
	 * @param formastter 日期显示格式 如yyyy-MM-dd
	 * @param days 1:昨天 2:前天 …… 依次类推
	 * @throws Exception
	 * @return String 格式化后的日期字符串
	 * @author wangxiaowei
	 */
	public static String getYesterdayStr(String formastter,Integer days) {

		return getLocalDate().minusDays(days).format(DateTimeFormatter.ofPattern(formastter));

	}
	
	
	/**
	 * 
	 * @description 按照指定格式获取日期字符串
	 * @param formastter 日期显示格式 如yyyy-MM-dd
	 * @throws Exception
	 * @return String 格式化后的日期字符串
	 * @author wangxiaowei
	 */
	public static String getDateStr(String formastter) {

		return getLocalDate().format(DateTimeFormatter.ofPattern(formastter));

	}

	/**
	 * 
	 * @description 指定格式字符串转换为LocalDateTime
	 * @param date 格式为:2019-07-16 12:12:12
	 * @return LocalDateTime    
	 * @author gemingming 
	 * @date 2019/07/16
	 */
	public static LocalDateTime parseDate(String date) {
        LocalDateTime localDateTime = null;

        try {
			localDateTime=LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			log.info(ErrorCode.CODE_1003001_MSG);
		}
		
      return   localDateTime;
	}
	
	
	/**
	 * 
	 * @description 按照指定格式将时间转换为字符串
	 * @param formastter 日期显示格式 如yyyy-MM-dd
	 * @throws Exception
	 * @return String 格式化后的日期字符串
	 * @author wangxiaowei
	 */
	public static String getDateStr(LocalDateTime localDateTime) {
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		return df.format(localDateTime);

	}
	
	/**
	 * 
	 * @description 判断时间格式是否为"yyyy-MM-dd"
	 * @param date 日期显示格式 如yyyy-MM-dd
	 * @throws Exception
	 * @return Boolean 
	 * @author 吴健
	 */
	public static Boolean isDate(String date) {
		
		String regex = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29) ";


		return date.matches(regex);

	}

}
