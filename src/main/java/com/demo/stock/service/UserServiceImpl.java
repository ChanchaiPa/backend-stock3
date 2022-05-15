package com.demo.stock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.stock.controller.request.UserRequest;
import com.demo.stock.exception.UserDuplicateException;
import com.demo.stock.model.User;
import com.demo.stock.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	
	@Override
	public User register(UserRequest userRequest) {
		User user = userRepo.findByUsername(userRequest.getUsername()).orElse(null);
		if (user == null) {
			user = new User();
			user.setUsername(userRequest.getUsername());
			user.setPassword(pwdEncoder.encode(userRequest.getPassword()));
			user.setRole(userRequest.getRole());
			userRepo.save(user);
		}
		else {
			throw new UserDuplicateException(userRequest.getUsername());
		}
		return user;
	}

	@Override
	public User findUserByUsername(String username) {
		return userRepo.findByUsername(username).orElse(null);
	}

}
