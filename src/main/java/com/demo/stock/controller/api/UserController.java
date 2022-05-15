package com.demo.stock.controller.api;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.stock.controller.request.UserRequest;
import com.demo.stock.exception.ReqValidationException;
import com.demo.stock.model.User;
import com.demo.stock.service.UserService;
import com.google.gson.Gson;

@RestController
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	/*@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User userModel) {
		Gson g = new Gson();
		System.out.println(g.toJson(userModel));
		String username = userModel.getUsername();
		String password = userModel.getPassword();
		if (username.equals("admin") && password.equals("power")) {
			return ResponseEntity.status(HttpStatus.OK).body("Login Success");
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login Fail");
		}
	}*/
	
	@PostMapping("/auth/register")
	public User register(@Valid @RequestBody UserRequest userRequest, BindingResult bindingResult) {
		Gson g = new Gson(); System.out.println(g.toJson(userRequest));
		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				throw new ReqValidationException(fieldError.getField() + ": " + fieldError.getDefaultMessage());
			});
		}
		return userService.register(userRequest);
	}

}
