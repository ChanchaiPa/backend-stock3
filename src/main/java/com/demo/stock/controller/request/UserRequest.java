package com.demo.stock.controller.request;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

	@Getter @Setter
	@NotEmpty
	@Length(min = 8, message = "Please enter at least {min} char")
	private String username;
	
	@Getter @Setter	
	@NotEmpty
	@Length(min = 8, message = "Please enter at least {min} char")
	private String password;
	
	@Getter @Setter	
	@NotEmpty
	private String role;

}
