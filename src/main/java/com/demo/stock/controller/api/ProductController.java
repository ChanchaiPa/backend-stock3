package com.demo.stock.controller.api;

import java.util.List;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.stock.controller.request.ProductRequest;
import com.demo.stock.exception.ProductNotFoundException;
import com.demo.stock.exception.ReqValidationException;
import com.demo.stock.model.Product;
import com.demo.stock.service.ProductService;


@RestController
//@CrossOrigin({"https://www.w3schools.com", "https://www.w3schools_dev.com"})
//@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST}, origins = {"https://www.w3schools.com", "https://www.w3schools_dev.com"})
public class ProductController {
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/product")  //http://127.0.0.1:8082/product
	public List<Product> getProducts() {
		return productService.getAllProduct();
	}
	
	@GetMapping("/product/{id}")  //http://127.0.0.1:8082/product/1
	public Product getProductById(@PathVariable int id) {
		return productService.getProductByID(id);
	}
		
	
	@PostMapping("/product")  //http://127.0.0.1:8082/product
	public Product addProduct(@Valid ProductRequest productRequest, BindingResult bindingResult) { 
		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				throw new ReqValidationException(fieldError.getField() + " Required..");
			});
		}
		return productService.addProduct(productRequest);
	}
	
	
	@PutMapping("/product")  //http://127.0.0.1:8082/product
	public Product updateProduct(@Valid ProductRequest productRequest, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				throw new ReqValidationException(fieldError.getField() + " Required..");
			});
		}
		return productService.updateProduct(productRequest);
	}
	
	
	@DeleteMapping("/product/{id}")  //http://127.0.0.1:8082/product/1
	public void deleteProductById(@PathVariable int id) {
		productService.deleteProduct(id);
	}
	
	
	
	//**********************************************************************
	
	
	
	// http://127.0.0.1:8082/product/get_by_name/banana
	@GetMapping(value = {"/product/get_by_name", "/product/get_by_name/{name}"})
	public Product getProductByName(@PathVariable(required = false) String name) {
		if (name == null)
			throw new ReqValidationException("Name");
		else	
			return productService.getProductByName(name);
	}
	
	// http://127.0.0.1:8082/product/get_by_stock?stock=10
	@GetMapping("/product/get_by_stock") 
	public List<Product> getProductByStock(@RequestParam(name="stock", defaultValue="0" ) int stock) {
		return productService.getProductByStock(stock);
	}
	
	// http://127.0.0.1:8082/product/get_by_price?price=10
	@GetMapping("/product/get_by_price") 
	public List<Product> getProductByPrice(@RequestParam(name="price", defaultValue="0" ) int price) {
		return productService.queryProductbyPrice(price);
	}	
	
	// http://127.0.0.1:8082/product/like_by_name/banana
	@GetMapping(value = {"/product/like_by_name", "/product/like_by_name/{name}"})
	public List<Product> searchProductByName(@PathVariable(required = false) String name) {
		if (name == null)
			throw new ReqValidationException("Name");
		else	
			return productService.searchProductByName(name);
	}	
	
}
