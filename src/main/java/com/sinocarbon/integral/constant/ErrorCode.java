package com.sinocarbon.integral.constant;

/**
 * 事例：2002001 2:服务级错误,1为系统级错误 002: 服务模块代码(即数据ID) 001: 具体错误代码
 */
public class ErrorCode {

	// 001 访问key（系统级)
	public static final Integer CODE_1001001 = 1001001;
	public static final String CODE_1001001_MSG = "KEY过期";

	// 002 redis（系统级)
	public static final Integer CODE_1002001 = 1002001;
	public static final String CODE_1002001_MSG = "redis异常";
	public static final Integer CODE_1002002 = 1002002;
	public static final String CODE_1002002_MSG = "递增因子必须大于0";
	public static final Integer CODE_1002003 = 1002003;
	public static final String CODE_1002003_MSG = "递减因子必须大于0";

	// 003 转换异常

	public static final Integer CODE_1003001 = 1003001;
	public static final String CODE_1003001_MSG = "时间转换异常";

	// 004 小程序解析异常
	public static final Integer CODE_1004001 = 1004001;
	public static final String CODE_1004001_MSG = "解析异常";

	// 服务级

	// 003 积分模块
	public static final Integer CODE_2003001 = 2003001;
	public static final String CODE_2003001_MSG = "缺少必要参数";
	public static final Integer CODE_2003002 = 2003002;
	public static final String CODE_2003002_MSG = "当前时间已存在数据";
	public static final Integer CODE_2003003 = 2003003;
	public static final String CODE_2003003_MSG = "参数不正确";
	public static final Integer CODE_2003004 = 2003004;
	public static final String CODE_2003004_MSG = "添加数据失败";
	public static final Integer CODE_2003005 = 2003005;
	public static final String CODE_2003005_MSG = "查无此记录";
	
	public static final Integer CODE_2003006 = 2003006;
	public static final String CODE_2003006_MSG = "非法参数";
	
	public static final Integer CODE_2003007 = 2003007;
	public static final String CODE_2003007_MSG = "余额不足";
	
	public static final Integer CODE_2003008 = 2003008;
	public static final String CODE_2003008_MSG = "重复操作";

	public static final Integer CODE_2003009 = 2003009;
	public static final String CODE_2003009_MSG = "时间格式异常";
	
	public static final Integer CODE_2003010 = 2003010;
	public static final String CODE_2003010_MSG = "超出早起打卡时间";

}
