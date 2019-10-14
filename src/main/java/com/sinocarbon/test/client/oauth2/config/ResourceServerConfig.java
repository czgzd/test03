package com.sinocarbon.test.client.oauth2.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.sinocarbon.test.client.oauth2.token.extractor.MyBearerTokenExtractor;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(false);
		resources.tokenExtractor(new MyBearerTokenExtractor());
	}
	
	@Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
        	.disable()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            .authorizeRequests()
            .antMatchers(
            		//swagger2生成离线文档时开启
//            		"/v2/api-docs",
//            		"/actuator/**",
//            		"/swagger-ui.html",
//            		"/webjars/**",
//            		"/swagger-resources/**",
            		//配置不需要鉴权的api
            		"/example/**",
            		"/api/**",
            		"/tEmp/**"
            		)
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }
}
