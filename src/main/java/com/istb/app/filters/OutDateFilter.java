package com.istb.app.filters;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class OutDateFilter extends OncePerRequestFilter {
	
	private static final int horaInicio = 0;
	
	private static final int horaCierre = 23;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		int hour = LocalDateTime.now().getHour();	
		if (hour >= horaInicio && hour < horaCierre) {
			filterChain.doFilter(request, response);
		} else {
			response.sendRedirect("/no-access?start=" + horaInicio + "&end="+ horaCierre);
		}
	}
}
