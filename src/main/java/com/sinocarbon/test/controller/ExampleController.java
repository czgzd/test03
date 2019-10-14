package com.sinocarbon.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sinocarbon.polaris.commons.pojo.LoginAppUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/example")
@Api(value = "案例121API,真实项目中请删除")
public class ExampleController {

///1121212121
	///121212121我改的
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	@PreAuthorize("hasRole('ROLE_ADMIN') AND hasRole('ROLE_DBA')")
	//嗯？？？
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADDUSER','ROLE_CHECKUSER','ROLE_EXPORTUSER')")
//	@PreAuthorize("hasAuthority('example:get/example/test') or hasRole('ROLE_SUPERADMIN')")
	@GetMapping("/111test")
	@ApiOperation(value = "findUserInfoByUsername", notes = "查询用户")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "username", value = "用户名", dataType = "String", required = true),
			@ApiImplicitParam(paramType = "query", name = "tenantId", value = "租户id", dataType = "String", required = true) })
	public String findUserInfoByUsername() {
		//葛明明修改2
		int a=1;  
		return  "111";
		//葛明明修改122
	}

	
}
	



