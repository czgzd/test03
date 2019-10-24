package com.sinocarbon.integral;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sinocarbon.integral.constant.IntegralConstant;

@SpringBootApplication
@MapperScan({IntegralConstant.MAPPER_SCAN_PACKAGE})
public class IntegralApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegralApplication.class, args);
	}

}
