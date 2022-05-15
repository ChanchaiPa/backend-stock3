package com.demo.stock.service_security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader!=null && authorizationHeader.length()>0) {
			UsernamePasswordAuthenticationToken authentication = getAuthorization(authorizationHeader);
			SecurityContextHolder.getContext().setAuthentication(authentication);			
		}
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthorization(String authorizationHeader) {
		String token = authorizationHeader.replaceAll("Bearer ", "");
		Claims claim = Jwts.parser().setSigningKey("my-key").parseClaimsJws(token).getBody();
		String username = (String)claim.get("subject");
		ArrayList<String> roles = (ArrayList<String>)claim.get("roles");
		
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		roles.stream().forEach(role -> { authorities.add(new SimpleGrantedAuthority(role)); });
		
		return new UsernamePasswordAuthenticationToken(username, null, authorities);
	}
	
}
