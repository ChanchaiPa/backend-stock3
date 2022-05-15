package com.demo.stock.service;

import com.demo.stock.controller.request.UserRequest;
import com.demo.stock.model.User;

public interface UserService {
	User register(UserRequest userRequest);
	User findUserByUsername(String username);
}
