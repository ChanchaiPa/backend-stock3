package com.demo.stock.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

	
	@Getter @Setter	
	@Positive
	private int code;
	
	@Getter @Setter	
	@NotEmpty
	private String name;
	
	@Getter @Setter	
	private MultipartFile file;	
	
	@Getter @Setter	
	private int price;	
	
	@Getter @Setter	
	private int stock;

}
