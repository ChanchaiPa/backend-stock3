package com.demo.stock.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.demo.stock.service_security.CustomUserDetailService;
import com.demo.stock.service_security.JWTAuthenticationFilter;
import com.demo.stock.service_security.JWTAuthorizationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	/*@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().
		antMatchers("/**").permitAll().
		and().
		exceptionHandling().authenticationEntryPoint((req, res, err)->{ res.sendError(HttpServletResponse.SC_UNAUTHORIZED); }).
		and().
		sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}*/

	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(pwdEncoder);
	}	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().
		antMatchers("/auth/register").permitAll().
		antMatchers(HttpMethod.DELETE, "/product/*").hasAnyAuthority("admin").
		anyRequest().authenticated().
		and().exceptionHandling().authenticationEntryPoint((req, res, err)->{ res.sendError(HttpServletResponse.SC_UNAUTHORIZED); }).
		and().addFilter(authenticationFilter()).sessionManagement().
		and().addFilter(new JWTAuthorizationFilter(authenticationManager())).sessionManagement().
		sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}	
	
	@Bean
	UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
		UsernamePasswordAuthenticationFilter filter = new JWTAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}
}
