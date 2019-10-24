package com.sinocarbon.integral.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class HeaderUtil {

	public static Integer getWxUserId() {
		Integer userId=0;
		String userIdSt = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getHeader("userId");
		if(userIdSt!=null&&!"".equals(userIdSt)) {
			userId=Integer.valueOf(userIdSt);
		}
		return userId;
	}
	
	
	
}
