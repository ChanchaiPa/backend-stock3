package com.demo.stock.service_security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.stock.model.User;
import com.demo.stock.service.UserService;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserService userService;	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserByUsername(username);
		if (user != null) {
			GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
			ArrayList<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(authority);
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
		}
		throw new UsernameNotFoundException("Username "+username+" not found");
	}

}
