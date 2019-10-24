package com.sinocarbon.integral.constant;

public class IntegralConstant {

	// 私有模块begin------------------------------------------------------------------------------------------

	public static final String REDIS_USER_SCORE_HASH = "user_score";
	public static final Integer INTEGRAL_LOG_DEFAULT_PAGENUMBER=20;	
	// 签到积分
	public static final Integer SIGN_INTEGRAL = 20;
	//首次登录赠送积分
	public static final Integer LOGIN_INTEGRAL = 10;


	// 私有模块end------------------------------------------------------------------------------------------

	// 公共模块begin------------------------------------------------------------------------------------------

	public static final String MAPPER_SCAN_PACKAGE = "com.sinocarbon.integral.dao";
	public static final String SUCCESS = "sucess";
	public static final Integer SUCCESS_FLAG = 1;
	public static final String FAIL = "fail";
	public static final String TOKEN="trdSession";

	// 操作类型
	public static final int OPERATION_WALK = 1;// 步行操作
	public static final int OPERATION_RIDING = 2;// 骑行操作
	public static final int OPERATION_SIGN = 3;// 早起打卡
	public static final int OPERATION_TASK_SUCCESS = 4;// 挑战成功
	public static final int OPERATION_TASK_FAIL = 5;// 挑战扣除
	public static final int OPERATION_LOGIN = 6;// 登录

	// 默认页码
	public static final Integer DEFAULT_PAGE_NUMBER = 1;
	

	//默认页面大小
	public static final Integer DEFAULT_PAGE_SIZE=10;
	public static final Integer DEFAULT_PAGE_SIZE_20=20;

	// redis模块

	/**
	 * redis session 里的用户信息 key
	 */
	public static final String SESSION_USER_INFO_KEY = "user_info";

	/**
	 * redis session 里的用户openId key
	 */
	public static final String SESSION_USER_OPENID_KEY = "user_openId";

	/**
	 * redis session 里的用户user_session_key key
	 */
	public static final String SESSION_USER_SESSION_KEY_KEY = "user_session_key";
	/** 减排折算系数map 名称 */
	public static final String EMISSION_COEFFICIENT_DICTIONARY_MAP = "emission_coefficient_dictionary_map";

	/** 调控系数 */
	public static final String CONTROL_COEFFICIENT_MAP = "control_coefficient_map";

	/** 积分获取途径 */
	public static final String OPERATION_TYPE_DICTIONARY_MAP = "operation_type_dictionary_map";

	/** app 积分获取途径 */
	public static final String APP_OPERATION_DICTIONARY_MAP = "app_operation_dictionary_map";

	/** 无极卡片比例信息 */
	public static final String INFINITY_CARD_MAP = "infinity_card_map";

	/** 用户信息数据 */
	public static final String USER_INFO_MAP = "user_info_map";

	/** 用户当天积分和减排信息 */
	public static final String USER_TODAY_INTEGRAL_EMISSION_MAP = "user_today_integral_emission_map";

	/** 用户每日积分排行前缀 */
	public static final String USER_TODAY_INTEGRAL_RANK_PREFIX = "today_integral_rank_";

	/** 用户总积分排行前缀 */
	public static final String USER_TOTAL_INTEGRAL_RANK_PREFIX = "total_integral_rank_";

	/** 用户运记录 */
	public static final String USER_TODAY_SPORT_RECORD_MAP = "user_today_sport_record_map";

	/** 用户日记 */
	public static final String USER_WEEK_DIARY_MAP = "user_week_diary_map";

	/** 用户步行 */
	public static final String WALKING = "walking";

	/** 用户骑行 */
	public static final String RIDING = "riding";

	// 公共模块end------------------------------------------------------------------------------------------

}
