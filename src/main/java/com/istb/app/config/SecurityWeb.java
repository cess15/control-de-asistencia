package com.istb.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.istb.app.services.security.SecurityService;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityWeb extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private SecurityService serviceSecurity;
	
	 @Override
	protected void configure(HttpSecurity http) throws Exception {
		 this.serviceSecurity.loadAccessControllList(http)
			.loadFormLogin(http)
			.loadLogout(http);
	}
	 
	 @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 this.serviceSecurity.loadAuthDetailService(auth);
	}
}
