package com.sinocarbon.integral.client.rest.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sinocarbon.polaris.commons.utils.BaseResponse;

@Service
public class ConfigCenterService {

	@Value("${configService.uri}")
	private  String configUrl;

	@Autowired
	private RestTemplate restTemplate;
	

	
	
	public BaseResponse getBudget(String registrationCode) {
		
		String url = configUrl+"/api/config/budget?registrationCode="+registrationCode;	

		
		return restTemplate.getForObject(url, BaseResponse.class);
		
	}
	
	
@SuppressWarnings("unchecked")
public Map<String,Double> getScale() {
		
		String url = configUrl+"/api/config/scale";	

		
		return restTemplate.getForObject(url, Map.class);
		
	}
	
	
	
	
	
}
