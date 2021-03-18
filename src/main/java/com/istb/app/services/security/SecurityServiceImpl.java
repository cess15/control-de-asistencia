package com.istb.app.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	public SecurityService loadAccessControllList(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.antMatchers("/", "/inicio", "/css/**", "/js/**", "/fonts/**", 
				"/dist/**","/images/**","/verify/**").permitAll()
			
			.antMatchers("/agregar-usuario","/eliminar-profesor/**","/asistencias","/periodo-profesores/**","/periodos/**","/inasistencias/**","/api/inasistenciasJustified","/inasistencias-justificadas/**","/data-profesores")
			.hasAuthority("Secretaria")

			.antMatchers("/inicio","/api/profile/avatar")
			.hasAnyAuthority("Secretaria","Docente")
			
			.antMatchers("/editar/**", "/perfil", "/api/inasistencias/unjustified","/api/inasistencias/justified", "/api/motivo/**", "/generar-justificativo/**","/generar-inasistencia/**","/justificar-inasistencia/**")
			.hasAuthority("Docente")
			
			.anyRequest().authenticated()
			.and().csrf().disable();
			
		return this;
	}

	@Override
	public SecurityService loadFormLogin(HttpSecurity http) throws Exception {
		
		http.formLogin()
			.loginPage("/login").defaultSuccessUrl("/inicio", true).permitAll();
			
		return this;
	}

	@Override
	public SecurityService loadLogout(HttpSecurity http) throws Exception {
		
		http.logout().deleteCookies("JSESSIONID");

		return this;
	}

	@Override
	public SecurityService loadAccessDenied(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecurityService loadAuthDetailService(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		return this;
	}

}
