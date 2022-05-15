package com.demo.stock.service_security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.demo.stock.controller.request.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	public JWTAuthenticationFilter() {
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		try {
			UserRequest userRequest = new ObjectMapper().readValue(request.getInputStream(), UserRequest.class);
			String username = userRequest.getUsername();
			String password = userRequest.getPassword();
			return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken( username, password, new ArrayList<GrantedAuthority>() ));
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)authResult.getPrincipal();
		String username = user.getUsername();
		ArrayList<String> authorities = new ArrayList<>();
		user.getAuthorities().stream().forEach(authority -> { authorities.add(authority.getAuthority()); });
		
		Date tokenExpired = new Date(System.currentTimeMillis() + 15*60*1000);
		Claims claim = Jwts.claims(); 
		claim.put("subject", username);
		claim.put("roles", authorities);
		String jwtToken = Jwts.builder().setClaims(claim).setExpiration(tokenExpired).signWith(SignatureAlgorithm.HS256, "my-key").compact();
		
		JSONObject result = new JSONObject();
		result.put("token", jwtToken);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(result.toString());
	}
	
	
}
